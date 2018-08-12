package cn.fiftys.weblog.pageviews;

import cn.fiftys.weblog.weborigin.WebLogBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PageViewReducer extends Reducer<Text, WebLogBean, NullWritable, Text> {

    Text v = new Text();

    @Override
    protected void reduce(Text key, Iterable<WebLogBean> values, Context context) throws IOException, InterruptedException {
        ArrayList<WebLogBean> beans = new ArrayList<>();
        //先将一个用户的所有访问记录中的时间拿出来排序
        try {
            for (WebLogBean bean : values) {
                WebLogBean webLogBean = new WebLogBean();
                try {
                    BeanUtils.copyProperties(webLogBean, bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                beans.add(webLogBean);
            }
            //将bean按照时间的先后顺序排序
            Collections.sort(beans, new Comparator<WebLogBean>() {
                @Override
                public int compare(WebLogBean o1, WebLogBean o2) {
                    try {
                        Date d1 = toDate(o1.getTimeLocal());
                        Date d2 = toDate(o2.getTimeLocal());

                        if (d1 == null || d2 == null){
                            return 0;
                        }
                        return d1.compareTo(d2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });

            /**
             * 以下逻辑为：从有序bean中分辨出各次visit，并对一次visit中所访问的page按顺序标号step
             * 核心思想：
             * 就是比较相邻两条记录中的时间差，如果时间差<30分钟，则该两条记录属于同一个session
             * 否则，就属于不同的session
             */
            int step = 1;
            String session = UUID.randomUUID().toString();
            for (int i = 0; i < beans.size(); i++) {
                WebLogBean bean = beans.get(i);
                if (1 == beans.size()) {
                    //设置停留时间默认为60s
                    // 设置默认停留时长为60s
                    v.set(session + "\001" + key.toString() + "\001" + bean.getRemoteUser() + "\001" + bean.getTimeLocal() + "\001" + bean.getRequest() + "\001" + step + "\001" + (60) + "\001" + bean.getHttpReferer() + "\001" + bean.getHttpUserAgent() + "\001" + bean.getBodyBytesSent() + "\001"
                            + bean.getStatus());
                    context.write(NullWritable.get(), v);
                    session = UUID.randomUUID().toString();
                    break;
                }
                // 如果不止1条数据，则将第一条跳过不输出，遍历第二条时再输出
                if (i == 0) {
                    continue;
                }
                //求近两次时间差
                long timeDiff = timeDiff(toDate(bean.getTimeLocal()), toDate(beans.get(i - 1).getTimeLocal()));
                // 如果本次-上次时间差<30分钟，则输出前一次的页面访问信息

                if (timeDiff < 30 * 60 * 1000) {

                    v.set(session + "\001" + key.toString() + "\001" + beans.get(i - 1).getRemoteUser() + "\001" + beans.get(i - 1).getTimeLocal() + "\001" + beans.get(i - 1).getRequest() + "\001" + step + "\001" + (timeDiff / 1000) + "\001" + beans.get(i - 1).getHttpReferer() + "\001"
                            + beans.get(i - 1).getHttpUserAgent() + "\001" + beans.get(i - 1).getBodyBytesSent() + "\001" + beans.get(i - 1).getStatus());
                    context.write(NullWritable.get(), v);
                    step++;
                } else {

                    // 如果本次-上次时间差>30分钟，则输出前一次的页面访问信息且将step重置，以分隔为新的visit
                    v.set(session + "\001" + key.toString() + "\001" + beans.get(i - 1).getRemoteUser() + "\001" + beans.get(i - 1).getTimeLocal() + "\001" + beans.get(i - 1).getRequest() + "\001" + (step) + "\001" + (60) + "\001" + beans.get(i - 1).getHttpReferer() + "\001"
                            + beans.get(i - 1).getHttpUserAgent() + "\001" + beans.get(i - 1).getBodyBytesSent() + "\001" + beans.get(i - 1).getStatus());
                    context.write(NullWritable.get(), v);
                    // 输出完上一条之后，重置step编号
                    step = 1;
                    session = UUID.randomUUID().toString();
                }

                // 如果此次遍历的是最后一条，则将本条直接输出
                if (i == beans.size() - 1) {
                    // 设置默认停留市场为60s
                    v.set(session + "\001" + key.toString() + "\001" + bean.getRemoteUser() + "\001" + bean.getTimeLocal() + "\001" + bean.getRequest() + "\001" + step + "\001" + (60) + "\001" + bean.getHttpReferer() + "\001" + bean.getHttpUserAgent() + "\001" + bean.getBodyBytesSent() + "\001" + bean.getStatus());
                    context.write(NullWritable.get(), v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.format(date);
    }

    private Date toDate(String timeStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.parse(timeStr);
    }

    private long timeDiff(String time1, String time2) throws ParseException {
        Date d1 = toDate(time1);
        Date d2 = toDate(time2);
        return d1.getTime() - d2.getTime();

    }

    private long timeDiff(Date time1, Date time2) throws ParseException {
        return time1.getTime() - time2.getTime();

    }


}

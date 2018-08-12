package cn.fiftys.weblog.visits;

import cn.fiftys.weblog.pageviews.PageViewsBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VisitsReducer extends Reducer<Text, PageViewsBean, NullWritable, VisitsBean> {
    @Override
    protected void reduce(Text session, Iterable<PageViewsBean> pvBeans, Context context) throws IOException, InterruptedException {
        // 将pvBeans按照step排序
        ArrayList<PageViewsBean> pvBeansList = new ArrayList<PageViewsBean>();
        for (PageViewsBean pvBean : pvBeans) {
            PageViewsBean bean = new PageViewsBean();
            try {
                BeanUtils.copyProperties(bean, pvBean);
                pvBeansList.add(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(pvBeansList, new Comparator<PageViewsBean>() {

            @Override
            public int compare(PageViewsBean o1, PageViewsBean o2) {

                return o1.getStep() > o2.getStep() ? 1 : -1;
            }
        });

        // 取这次visit的首尾pageview记录，将数据放入VisitBean中
        VisitsBean visitBean = new VisitsBean();
        // 取visit的首记录
        visitBean.setInPage(pvBeansList.get(0).getRequest());
        visitBean.setInTime(pvBeansList.get(0).getTimestr());
        // 取visit的尾记录
        visitBean.setOutPage(pvBeansList.get(pvBeansList.size() - 1).getRequest());
        visitBean.setOutTime(pvBeansList.get(pvBeansList.size() - 1).getTimestr());
        // visit访问的页面数
        visitBean.setPageVisits(pvBeansList.size());
        // 来访者的ip
        visitBean.setRemoteAddr(pvBeansList.get(0).getRemoteAddr());
        // 本次visit的referal
        visitBean.setReferal(pvBeansList.get(0).getReferal());
        visitBean.setSession(session.toString());

        context.write(NullWritable.get(), visitBean);


    }

}

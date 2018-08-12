package cn.fiftys.weblog.weborigin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

public class WebLogParser {

    public static SimpleDateFormat df1 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
    public static SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public static WebLogBean parser(String line) {
        WebLogBean webLogBean = new WebLogBean();
        String[] arr = line.split(" ");
        if (arr.length > 11) {
            webLogBean.setRemoteAddr(arr[0]);
            webLogBean.setRemoteUser(arr[1]);
            String timeLocal = formatDate(arr[3].substring(1));
            if (null == timeLocal || "".equals(timeLocal)) {
                timeLocal = "-invalid_time-";
            }
            webLogBean.setTimeLocal(timeLocal);

            webLogBean.setRequest(arr[6]);
            webLogBean.setStatus(arr[8]);
            webLogBean.setBodyBytesSent(arr[9]);
            webLogBean.setHttpReferer(arr[10]);

            //如果useragent元素较多,拼接useranger
            if (arr.length > 12) {
                StringBuilder sb = new StringBuilder();
                for (int i = 11; i < arr.length; i++) {
                    sb.append(arr[i]);
                }
                webLogBean.setHttpUserAgent(sb.toString());
            } else {
                webLogBean.setHttpUserAgent(arr[11]);
            }
            System.out.println("webLogBean.getStatus() "+webLogBean.getStatus());
            System.out.println("webLogBean.getStatus() "+webLogBean);
            if (Integer.parseInt(webLogBean.getStatus()) >= 400) {
                webLogBean.setValid(false);
            }

            if ("-invalid_time-".equals(webLogBean.getTimeLocal())) {
                webLogBean.setValid(false);
            }
        } else {
            webLogBean = null;
        }
        return webLogBean;
    }

    public static void filtStaticResource(WebLogBean bean, Set<String> pages) {
        if (!pages.contains(bean.getRequest())) {
            bean.setValid(false);
        }
    }

    public static String formatDate(String timeLocal) {
        try {
            return df2.format(df1.parse(timeLocal));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

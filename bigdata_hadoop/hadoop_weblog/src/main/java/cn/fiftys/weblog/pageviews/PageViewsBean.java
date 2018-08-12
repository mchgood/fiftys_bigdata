package cn.fiftys.weblog.pageviews;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * pageviews封装bean对象
 *
 * @author yummy
 */
public class PageViewsBean implements Writable {

    private String session;
    private String remoteAddr;
    private String timestr;
    private String request;
    private int    step;
    private String staylong;
    private String referal;
    private String useragent;
    private String bytesSend;
    private String status;

    public void set(String session, String remoteAddr, String useragent, String timestr, String request, int step, String staylong, String referal, String bytesSend, String status) {
        this.session = session;
        this.remoteAddr = remoteAddr;
        this.useragent = useragent;
        this.timestr = timestr;
        this.request = request;
        this.step = step;
        this.staylong = staylong;
        this.referal = referal;
        this.bytesSend = bytesSend;
        this.status = status;
    }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(session);
        out.writeUTF(remoteAddr);
        out.writeUTF(timestr);
        out.writeUTF(request);
        out.writeInt(step);
        out.writeUTF(staylong);
        out.writeUTF(referal);
        out.writeUTF(useragent);
        out.writeUTF(bytesSend);
        out.writeUTF(status);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.session = in.readUTF();
        this.remoteAddr = in.readUTF();
        this.timestr = in.readUTF();
        this.request = in.readUTF();
        this.step = in.readInt();
        this.staylong = in.readUTF();
        this.referal = in.readUTF();
        this.useragent = in.readUTF();
        this.bytesSend = in.readUTF();
        this.status = in.readUTF();
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getTimestr() {
        return timestr;
    }

    public void setTimestr(String timestr) {
        this.timestr = timestr;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getStaylong() {
        return staylong;
    }

    public void setStaylong(String staylong) {
        this.staylong = staylong;
    }

    public String getReferal() {
        return referal;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public String getBytesSend() {
        return bytesSend;
    }

    public void setBytesSend(String bytesSend) {
        this.bytesSend = bytesSend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

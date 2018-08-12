package cn.fiftys.weblog.weborigin;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author yummy
 */
public class WebLogBean implements Writable {

    /**
     * 判断数据是否合法
     */
    private boolean valid = true;


    /**
     * 记录客户端的ip地址
     */
    private String remoteAddr;

    /**
     * 记录客户端用户名称,忽略属性"-"
     */
    private String remoteUser;

    /**
     * 记录访问时间与时区
     */
    private String timeLocal;

    /**
     * 记录请求的url与http协议
     */
    private String request;

    /**
     * 记录请求状态；成功是200
     */
    private String status;

    /**
     * 记录发送给客户端文件主体内容大小
     */
    private String bodyBytesSent;

    /**
     * 用来记录从那个页面链接访问过来的
     */
    private String httpReferer;

    /**
     * 记录客户浏览器的相关信息
     */
    private String httpUserAgent;


    public void set(boolean valid, String remoteAddr, String remoteUser, String timeLocal, String request, String status, String bodyBytesSent, String httpReferer, String httpUserAgent) {
        this.valid = valid;
        this.remoteAddr = remoteAddr;
        this.remoteUser = remoteUser;
        this.timeLocal = timeLocal;
        this.request = request;
        this.status = status;
        this.bodyBytesSent = bodyBytesSent;
        this.httpReferer = httpReferer;
        this.httpUserAgent = httpUserAgent;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeBoolean(this.valid);
        out.writeUTF(null == remoteAddr ? "" : remoteAddr);
        out.writeUTF(null == remoteUser ? "" : remoteUser);
        out.writeUTF(null == timeLocal ? "" : timeLocal);
        out.writeUTF(null == request ? "" : request);
        out.writeUTF(null == status ? "" : status);
        out.writeUTF(null == bodyBytesSent ? "" : bodyBytesSent);
        out.writeUTF(null == httpReferer ? "" : httpReferer);
        out.writeUTF(null == httpUserAgent ? "" : httpUserAgent);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.valid = in.readBoolean();
        this.remoteAddr = in.readUTF();
        this.remoteUser = in.readUTF();
        this.timeLocal = in.readUTF();
        this.request = in.readUTF();
        this.status = in.readUTF();
        this.bodyBytesSent = in.readUTF();
        this.httpReferer = in.readUTF();
        this.httpUserAgent = in.readUTF();

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.valid);
        sb.append("\001").append(this.getRemoteAddr());
        sb.append("\001").append(this.getRemoteUser());
        sb.append("\001").append(this.getTimeLocal());
        sb.append("\001").append(this.getRequest());
        sb.append("\001").append(this.getStatus());
        sb.append("\001").append(this.getBodyBytesSent());
        sb.append("\001").append(this.getHttpReferer());
        sb.append("\001").append(this.getHttpUserAgent());
        return sb.toString();
    }


    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public String getTimeLocal() {
        return timeLocal;
    }

    public void setTimeLocal(String timeLocal) {
        this.timeLocal = timeLocal;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBodyBytesSent() {
        return bodyBytesSent;
    }

    public void setBodyBytesSent(String bodyBytesSent) {
        this.bodyBytesSent = bodyBytesSent;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }
}

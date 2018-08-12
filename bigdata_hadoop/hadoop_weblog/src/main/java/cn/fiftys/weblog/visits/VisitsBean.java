package cn.fiftys.weblog.visits;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class VisitsBean implements Writable {

    private String session;
    private String remoteAddr;
    private String inTime;
    private String outTime;
    private String inPage;
    private String outPage;
    private String referal;
    private int pageVisits;


    public void set(String session, String remoteAddr, String inTime, String outTime, String inPage, String outPage, String referal, int pageVisits) {
        this.session = session;
        this.remoteAddr = remoteAddr;
        this.inTime = inTime;
        this.outTime = outTime;
        this.inPage = inPage;
        this.outPage = outPage;
        this.referal = referal;
        this.pageVisits = pageVisits;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(session);
        out.writeUTF(remoteAddr);
        out.writeUTF(inTime);
        out.writeUTF(outTime);
        out.writeUTF(inPage);
        out.writeUTF(outPage);
        out.writeUTF(referal);
        out.writeInt(pageVisits);

    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.session = in.readUTF();
        this.remoteAddr = in.readUTF();
        this.inTime = in.readUTF();
        this.outTime = in.readUTF();
        this.inPage = in.readUTF();
        this.outPage = in.readUTF();
        this.referal = in.readUTF();
        this.pageVisits = in.readInt();
    }

    @Override
    public String toString() {
        return session + "\001" + remoteAddr + "\001" + inTime + "\001" + outTime + "\001" + inPage + "\001" + outPage + "\001" + referal + "\001" + pageVisits;
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

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getInPage() {
        return inPage;
    }

    public void setInPage(String inPage) {
        this.inPage = inPage;
    }

    public String getOutPage() {
        return outPage;
    }

    public void setOutPage(String outPage) {
        this.outPage = outPage;
    }

    public String getReferal() {
        return referal;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }

    public int getPageVisits() {
        return pageVisits;
    }

    public void setPageVisits(int pageVisits) {
        this.pageVisits = pageVisits;
    }
}

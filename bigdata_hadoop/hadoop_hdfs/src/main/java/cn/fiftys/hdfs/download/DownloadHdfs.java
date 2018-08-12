package cn.fiftys.hdfs.download;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 *
 * 使用JavaAPI操作HDFS下载文件
 * @author yummy
 */
public class DownloadHdfs {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();

        //指定使用的是hdfs文件系统
        conf.set("fs.defaultFS","hdfs://node1:9000");

        //通过如下的方式进行客户端身份的设置
        System.setProperty("HADOOP_USER_NAME","root");

        //通过使用FileSystem的静态方法获取到文件系统客户端对象
        FileSystem fs = FileSystem.get(conf);

        //第一个path:表示虚拟机上文件地址
        //第二个path:表示下载到本地哪里
        fs.copyToLocalFile(new Path("/1.txt"),new Path("d://"));

        //释放资源
        fs.close();
    }
}

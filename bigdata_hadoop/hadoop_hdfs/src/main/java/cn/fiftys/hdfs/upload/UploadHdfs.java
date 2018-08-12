package cn.fiftys.hdfs.upload;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * 使用javaAPI上传文件到HDFS集群
 * @author yummy
 */
public class UploadHdfs {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();

        //指定使用的是hdfs文件系统
        conf.set("fs.defaultFS","hdfs://node1:9000");

        //通过如下的方式进行客户端身份的设置
        System.setProperty("HADOOP_USER_NAME","root");

        //通过使用FileSystem的静态方法获取到文件系统客户端对象
        FileSystem fs = FileSystem.get(conf);

        //上传文件到hdfs文件系统根目录下
        fs.copyFromLocalFile(new Path("e:/aaa.txt"),new Path("/"));

        //释放资源
        fs.close();
    }
}

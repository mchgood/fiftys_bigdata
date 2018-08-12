package cn.fiftys.map.mapjoin;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

/**
 * mapjoin运行程序主类
 * @author yummy
 */
public class MapJoinDriver {
    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance();

        job.setJarByClass(MapJoinDriver.class);
        job.setMapperClass(MapJoinMapper.class);
        job.addCacheFile(new URI("file:/D:/mappertest/mapjoin/cache/products.txt"));

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setNumReduceTasks(1);

        FileInputFormat.setInputPaths(job,"D:\\mappertest\\mapjoin\\input");
        FileOutputFormat.setOutputPath(job,new Path("D:\\mappertest\\mapjoin\\output"));

        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);
    }
}

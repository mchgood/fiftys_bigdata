package cn.fiftys.map.distinct;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * mr程序数据去重输出类
 *
 * @author yummy
 */
public class DistinctDriver {
    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance();

        job.setJarByClass(DistinctDriver.class);
        job.setMapperClass(DistinctMapper.class);
        job.setReducerClass(DistinctReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, "D:\\mappertest\\distinct\\input");
        FileOutputFormat.setOutputPath(job, new Path("D:\\mappertest\\distinct\\output"));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }


}

package cn.fiftys.map.topn;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * mr程序topN的输出类
 * @author yummy
 */
public class TopNDriver {
    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance();

        job.setJarByClass(TopNDriver.class);
        job.setMapperClass(TopNMapper.class);
        job.setReducerClass(TopNReduce.class);


        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(IntWritable.class);


        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, "D:\\mappertest\\topn\\input");
        FileOutputFormat.setOutputPath(job, new Path("D:\\mappertest\\topn\\output"));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }


}

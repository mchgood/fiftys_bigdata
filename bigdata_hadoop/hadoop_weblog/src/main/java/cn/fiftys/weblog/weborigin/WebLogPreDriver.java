package cn.fiftys.weblog.weborigin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WebLogPreDriver {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //设置运行主类
        job.setJarByClass(WebLogPreDriver.class);

        //设置运行mapper
        job.setMapperClass(WebLogPreMapper.class);

        //设置mapper阶段输出key,value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(job,"D:\\wordcount\\input");
        FileOutputFormat.setOutputPath(job,new Path("D:\\wordcount\\output"));

        //提交
        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);
    }
}

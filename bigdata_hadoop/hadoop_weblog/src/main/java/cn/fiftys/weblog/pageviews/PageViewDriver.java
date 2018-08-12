package cn.fiftys.weblog.pageviews;

import cn.fiftys.weblog.weborigin.WebLogBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageViewDriver {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(PageViewDriver.class);
        job.setMapperClass(PageViewMapper.class);
        job.setReducerClass(PageViewReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(WebLogBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job,"D:\\wordcount\\output");
        FileOutputFormat.setOutputPath(job,new Path("D:\\wordcount\\pageviewput2"));

        //提交
        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);


    }

}

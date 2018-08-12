package cn.fiftys.weblog.visits;

import cn.fiftys.weblog.pageviews.PageViewsBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class VisitsDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(VisitsDriver.class);
        job.setMapperClass(VisitsMapper.class);
        job.setReducerClass(VisitsReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(PageViewsBean.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(VisitsBean.class);

        FileInputFormat.setInputPaths(job,"D:\\wordcount\\pageviewput");
        FileOutputFormat.setOutputPath(job,new Path("D:\\wordcount\\visitsput"));

        //提交
        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);



    }


}

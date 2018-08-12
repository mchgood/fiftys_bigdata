package cn.fiftys.map.wordcount;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * wordcount输出类
 * @author yummy
 */
public class WordCountDriver {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);

        //指定本次mr程序运行的主类
        job.setJarByClass(WordCountDriver.class);
        //指定本次mr程序mapper阶段主类
        job.setMapperClass(WordCountMapper.class);
        //指定本次mr程序reduce阶段运行主类
        job.setReducerClass(WordCountReduce.class);

        //指定本次mapper阶段输出数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //指定本次mr程序最终输出结果类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //指定本次mr程序读取数据路径
        FileInputFormat.setInputPaths(job,"D:\\wordcount11\\input");
        //指定本次mr程序输出数据路径
        FileOutputFormat.setOutputPath(job,new Path("D:\\wordcount11\\output"));

        //提交程序
        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);


    }
}

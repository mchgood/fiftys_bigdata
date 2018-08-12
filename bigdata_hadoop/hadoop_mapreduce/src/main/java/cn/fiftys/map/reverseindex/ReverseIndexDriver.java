package cn.fiftys.map.reverseindex;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author yummy
 */
public class ReverseIndexDriver {
    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance();

        job.setJarByClass(ReverseIndexDriver.class);

        job.setMapperClass(ReverseIndexMapper.class);
        job.setCombinerClass(ReverseIndexConbiner.class);
        job.setReducerClass(ReverseIndexReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, "D:\\mappertest\\index\\input");
        FileOutputFormat.setOutputPath(job, new Path("D:\\mappertest\\index\\output"));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);

    }
}

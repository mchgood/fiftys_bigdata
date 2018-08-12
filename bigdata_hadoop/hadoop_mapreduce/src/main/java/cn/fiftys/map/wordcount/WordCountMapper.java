package cn.fiftys.map.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * mapreduce入门案例:wordcount,统计单词出现的次数
 *
 * @author yummy
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    Text        k = new Text();
    IntWritable v = new IntWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取到每一行,按照空格进行切割
        String[] words = value.toString().trim().split(" ");
        //遍历获取每一个单词,并标记为1
        for (String word : words) {
            k.set(word);
            v.set(1);
            //使用mr程序的上下文context  把mapper阶段处理的数据发送出去
            //作为reduce 阶段的输入数据
            context.write(k, v);
        }
    }
}

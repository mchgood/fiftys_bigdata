package cn.fiftys.map.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * wordCount案例中reeduc阶段
 * 此阶段负责统计单词出现的次数
 */
public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

    IntWritable v = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //定义一个计数器器
        int count = 0;
        //遍历一组迭代器,把每一个数量相同的单词累加起来
        for (IntWritable value : values) {
            count += value.get();
        }
        v.set(count);
        //输出最终结果
        context.write(key, v);

    }
}

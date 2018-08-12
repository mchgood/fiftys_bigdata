package cn.fiftys.map.topn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * mr程序TopN,求出最大的5个数字
 *
 * @author yummy
 */
public class TopNMapper extends Mapper<LongWritable, Text, NullWritable, IntWritable> {

    TreeSet<Integer> ts = new TreeSet<>();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().trim().split(" ");
        for (String s : arr) {
            ts.add(Integer.parseInt(s));
            if (ts.size() > 5) {
                ts.remove(ts.first());
            }
        }
    }

    NullWritable k = NullWritable.get();
    IntWritable  v = new IntWritable();

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        Iterator<Integer> i = ts.iterator();
        while (i.hasNext()) {
            Integer next = i.next();
            v.set(next);
            context.write(k, v);
        }


    }
}

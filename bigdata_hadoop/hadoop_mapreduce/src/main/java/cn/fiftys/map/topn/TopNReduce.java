package cn.fiftys.map.topn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

public class TopNReduce  extends Reducer<NullWritable, IntWritable,NullWritable, IntWritable>{
    TreeSet<Integer> ts = new TreeSet<>();

    NullWritable k = NullWritable.get();
    IntWritable v = new IntWritable();

    @Override
    protected void reduce(NullWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<IntWritable> iterator = values.iterator();

        while (iterator.hasNext()){
            IntWritable next = iterator.next();
            ts.add(next.get());
            if (ts.size() >5){
                ts.remove(ts.first());
            }
        }

        Iterator<Integer> iterator1 = ts.iterator();
        while (iterator1.hasNext()) {
            Integer next = iterator1.next();
            v.set(next);
            context.write(k,v);
        }
    }
}

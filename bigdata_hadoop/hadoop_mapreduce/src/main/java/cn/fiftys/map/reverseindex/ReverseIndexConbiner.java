package cn.fiftys.map.reverseindex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author yummy
 */
public class ReverseIndexConbiner extends Reducer<Text, Text, Text, Text> {
    Text v = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        Iterator<Text> iterator = values.iterator();
        while (iterator.hasNext()) {
            Text next = iterator.next();
            sum += Integer.parseInt(next.toString());
        }
        int indexOf = key.toString().indexOf(":");
        //重新设置value值由URL和词频组成
        v.set(key.toString().substring(indexOf + 1).concat(":") + sum);
        //重新设置key的值为单词
        key.set(key.toString().substring(0, indexOf));
        context.write(key, v);
    }
}

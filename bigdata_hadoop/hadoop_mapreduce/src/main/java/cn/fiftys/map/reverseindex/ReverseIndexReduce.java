package cn.fiftys.map.reverseindex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * ReverseIndex程序reduc类
 * @author yummy
 */
public class ReverseIndexReduce extends Reducer<Text, Text, Text, Text> {
    Text result = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //生成文档文档
        String fileList = new String();
        for (Text value : values) {
            fileList += value.toString() + ";";
        }
        result.set(fileList);
        context.write(key, result);
    }
}

package cn.fiftys.map.reverseindex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * mr程序重构索引
 *
 * @author yummy
 */
public class ReverseIndexMapper extends Mapper<LongWritable, Text, Text, Text> {
    Text k = new Text();
    Text v = new Text("1");

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //得到这行数据所在的文件切片
        FileSplit fileSplit = (FileSplit) context.getInputSplit();

        //根据文件切片得到文件名
        String filename = fileSplit.getPath().getName();
        String[] arr = value.toString().split("\\s+");

        for (String letter : arr) {
            //key值由单词还有URL组成,如"hello:file1.txt"
            k.set(letter.concat(":").concat(filename));
            context.write(k, v);
        }
    }
}

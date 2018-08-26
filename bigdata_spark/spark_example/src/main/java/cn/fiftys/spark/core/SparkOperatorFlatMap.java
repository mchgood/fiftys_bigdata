package cn.fiftys.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SparkOperatorFlatMap {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("SparkOperatorFlatMap")
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        //构造集合,将文本拆分为单词
        List<String> stringNumber = Arrays.asList(
                "hello word hello spark hello hadoop",
                "my mama dont like you");

        JavaRDD<String> stringNumberRDD = sc.parallelize(stringNumber);
        //执行FlatMap算子,将每一行文本拆分为多个单词
        //第二个泛型参数指定:代表了返回新元素的类型
        //call 方法返回的类型为Iterator迭代器
        //flatMap其实就是,接收原RDD中的每一个元素,并进行各种逻辑的计算和处理
        //              返回多个元素,封装在Iterator中
        //新的RDD中即封装了所有新的元素
        JavaRDD<String> splitNumberRDD = stringNumberRDD.flatMap(
                new FlatMapFunction<String, String>() {
                    public Iterator<String> call(String s) throws Exception {
                        return Arrays.asList(s.split(" ")).iterator();
                    }
                });
        splitNumberRDD.foreach(new VoidFunction<String>() {
            public void call(String s) throws Exception {
                System.out.println(s);
            }
        });

        sc.stop();
    }
}

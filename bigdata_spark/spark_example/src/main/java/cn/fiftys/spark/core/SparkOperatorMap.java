package cn.fiftys.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;
import java.util.List;

/**
 * Transformation操作Map算子
 */
public class SparkOperatorMap {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("SparkOperatorMap")
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        //构建集合
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        //并行化集合,创建初始RDD
        //SparkContext中parallelize方法将一个集合构建初始RDD
        JavaRDD<Integer> numberRDD = sc.parallelize(numbers);

        //使用Map算子,将集合中每一个元素都乘以2
        //Map算子,是对任何类型的RDD都是可以调用的
        //在Java中,map算子接收的参数是Function对象
        //在Funtion中会设置第二个泛型参数,这个泛型参数就是返回新元素的类型
        //同时call方法中的返回值也要与第二个泛型参数保持一致
        //在call方法内部,可以对原始RDD中的每一个元素进行各种处理与计算,并返回一个新的RDD
        JavaRDD<Integer> multipNumber = numberRDD.map(
                new Function<Integer, Integer>() {
                    public Integer call(Integer v1) throws Exception {
                        return v1 * 2;
                    }
                });
        multipNumber.foreach(new VoidFunction<Integer>() {
            public void call(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
        //关闭JavaSparkContext
        sc.stop();


    }
}

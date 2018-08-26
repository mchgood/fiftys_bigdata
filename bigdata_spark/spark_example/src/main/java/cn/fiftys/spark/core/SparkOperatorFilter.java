package cn.fiftys.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;
import java.util.List;

/**
 * filter算子案例,过滤集合中的偶数
 */
public class SparkOperatorFilter {
    public static void main(String[] args) {
        //创建SparkConf
        SparkConf conf = new SparkConf().setAppName("SparkOperatorFilter").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //模拟集合
        List<Integer> number = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        //创建初始化RDD
        JavaRDD<Integer> numberRDD = sc.parallelize(number);

        //执行Filter算子.
        //与map不同,方法返回值是boolean类型
        //每一个初始的RDD中的元素都会传入到call方法中
        //如果你想在新的RDD中保留这个元素,那么就返回true,不想保留返回false
        JavaRDD<Integer> filterNumberRDD = numberRDD.filter(
                new Function<Integer, Boolean>() {
                    public Boolean call(Integer v1) throws Exception {
                        return v1 % 2 == 0;
                    }
                });
        filterNumberRDD.foreach(new VoidFunction<Integer>() {
            public void call(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
        sc.stop();


    }
}

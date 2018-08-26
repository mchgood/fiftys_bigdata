package cn.fiftys.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * 统计每个班级的总分
 */
public class SparkOperatorReduceByKey {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf()
                .setAppName("SparkOperatorReduceByKey")
                .setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 模拟集合
        List<Tuple2<String, Integer>> scoreList = Arrays.asList(
                new Tuple2<String, Integer>("class1", 80),
                new Tuple2<String, Integer>("class2", 75),
                new Tuple2<String, Integer>("class1", 90),
                new Tuple2<String, Integer>("class2", 65));
        //并行化集合，创建JavaPairRDD
        JavaPairRDD<String, Integer> scores = sc.parallelizePairs(scoreList);

        // reduceByKey，接收的参数是Function2类型，它有三个泛型参数，实际上代表了三个值
        // 第一个泛型类型和第二个泛型类型，代表了原始RDD中的元素的value的类型
        // 因此对每个key进行reduce，都会依次将第一个、第二个value传入，
        // 将值再与第三个value传入
        // 因此此处，会自动定义两个泛型类型，代表call()方法的两个传入参数的类型
        // 第三个泛型类型，代表了每次reduce操作返回的值的类型，
        // 默认也是与原始RDD的value类型相同的
        // reduceByKey算法返回的RDD，还是JavaPairRDD<key, value>
        JavaPairRDD<String, Integer> totalScores = scores.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    public Integer call(Integer v1, Integer v2) throws Exception {
                        return v1 + v2;
                    }
                });

        totalScores.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            public void call(Tuple2<String, Integer> t) throws Exception {
                System.out.println(t._1 + ":" + t._2);
            }
        });

        sc.stop();
    }


}

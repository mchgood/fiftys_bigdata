package cn.fiftys.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class SparkOperatorJoin {
    public static void main(String[] args) {
        // 创建SparkConf
        SparkConf conf = new SparkConf()
                .setAppName("SparkOperatorJoin")
                .setMaster("local");
        // 创建JavaSparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 模拟集合
        List<Tuple2<Integer, String>> studentList = Arrays.asList(
                new Tuple2<Integer, String>(1, "leo"),
                new Tuple2<Integer, String>(2, "jack"),
                new Tuple2<Integer, String>(3, "tom"));

        List<Tuple2<Integer, Integer>> scoreList = Arrays.asList(
                new Tuple2<Integer, Integer>(1, 100),
                new Tuple2<Integer, Integer>(2, 90),
                new Tuple2<Integer, Integer>(3, 60));

        // 并行化两个RDD
        JavaPairRDD<Integer, String> students = sc.parallelizePairs(studentList);
        JavaPairRDD<Integer, Integer> scores = sc.parallelizePairs(scoreList);

        // 使用join算子关联两个RDD
        // join以后，还是会根据key进行join，并返回JavaPairRDD
        // 但是JavaPairRDD的第一个泛型类型，之前两个JavaPairRDD的key的类型，
        // 因为是通过key进行join的
        // 第二个泛型类型，是Tuple2<v1, v2>的类型，
        // Tuple2的两个泛型分别为原始RDD的value的类型
        // join，就返回的RDD的每一个元素，就是通过key join上的一个pair
        // 什么意思呢？比如有(1, 1) (1, 2) (1, 3)的一个RDD
        // 还有一个(1, 4) (2, 1) (2, 2)的一个RDD
        // join以后，实际上会得到(1 (1, 4)) (1, (2, 4)) (1, (3, 4))
        JavaPairRDD<Integer, Tuple2<String, Integer>> studentScores = students.join(scores);

        studentScores.foreach(
                new VoidFunction<Tuple2<Integer, Tuple2<String, Integer>>>() {
                    public void call(Tuple2<Integer, Tuple2<String, Integer>> t) throws Exception {
                        System.out.println("student id: " + t._1);
                        System.out.println("student name: " + t._2._1);
                        System.out.println("student score: " + t._2._2);
                        System.out.println("===============================");
                    }
                });


        sc.stop();

    }
}

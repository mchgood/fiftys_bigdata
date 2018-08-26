package cn.fiftys.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class SparkOperatorSortBykey {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("SparkOperatorSortBykey").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Tuple2<Integer, String>> scoreList = Arrays.asList(
                new Tuple2<Integer, String>(99, "yummy"),
                new Tuple2<Integer, String>(98, "leo"),
                new Tuple2<Integer, String>(95, "tom"),
                new Tuple2<Integer, String>(91, "jack"));

        JavaPairRDD<Integer, String> scores = sc.parallelizePairs(scoreList);
        // 对scores RDD执行sortByKey算子
        // sortByKey其实就是根据key进行排序，可以手动指定升序，或者降序
        // 返回的，还是JavaPairRDD，其中的元素内容，都是和原始的RDD一模一样的
        // 但是就是RDD中的元素的顺序，不同了
        JavaPairRDD<Integer, String> sortedScores = scores.sortByKey(false);

        sortedScores.foreach(new VoidFunction<Tuple2<Integer, String>>() {
            public void call(Tuple2<Integer, String> t) throws Exception {
                System.out.println(t._1+":"+t._2);
            }
        });

        sc.stop();
    }
}

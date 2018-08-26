package cn.fiftys.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * groupByKey:按照班级对成绩进行分组
 */
public class SparkOperatorGroupByKey {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("SparkOperatorGroupByKey")
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        //模拟集合
        List<Tuple2<String, Integer>> scoreList = Arrays.asList(
                new Tuple2<String, Integer>("calss1", 90),
                new Tuple2<String, Integer>("calss2", 70),
                new Tuple2<String, Integer>("calss1", 75),
                new Tuple2<String, Integer>("calss2", 95)
        );

        JavaPairRDD<String, Integer> listRDD = sc.parallelizePairs(scoreList);
        // groupByKey算子，返回的还是JavaPairRDD
        // 但是，JavaPairRDD的第一个泛型类型不变，
        // 第二个泛型类型变成Iterable这种集合类型
        // 也就是说，按照了key进行分组，那么每个key可能都会有多个value，
        // 此时多个value聚合成了Iterable
        JavaPairRDD<String, Iterable<Integer>> result = listRDD.groupByKey();

        result.foreach(new VoidFunction<Tuple2<String, Iterable<Integer>>>() {
            public void call(
                    Tuple2<String, Iterable<Integer>> t) throws Exception {
                System.out.println("class:" + t._1);
                Iterator<Integer> ite = t._2.iterator();
                while (ite.hasNext()) {
                    System.out.println(ite.next());
                }
                System.out.println("==================");
            }
        });

        sc.stop();
    }
}

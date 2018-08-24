package cn.fiftys.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 使用java操作spark开发wordcount案例
 */
public class WordCountLocal {
    public static void main(String[] args){
        SparkConf conf = new SparkConf().setAppName("WordCountLocal").setMaster("local");

        //创建javaSparkContext对象
        JavaSparkContext sc = new JavaSparkContext(conf);

        //要针对输入源,创建一个初始的RDD
        JavaRDD<String> lines = sc.textFile("D:\\file1.txt");

        //读取文件,将一行单词拆分为多个单词
       JavaRDD<String> words =  lines.flatMap(new FlatMapFunction<String, String>() {
            public Iterator<String> call(String line) throws Exception {
                String[] words = line.trim().split(" ");
                return  Arrays.asList(words).iterator();
            }
        });

       //每个单词记为1
        JavaPairRDD<String, Integer> wordAndOne = words.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        //相同单词出现次数累加
        JavaPairRDD<String, Integer> result = wordAndOne.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        //按照单词出现的次数进行降序排列
        JavaPairRDD<Integer, String> reverseJavaRDD = result.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            public Tuple2<Integer, String> call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return new Tuple2<Integer, String>(stringIntegerTuple2._2, stringIntegerTuple2._1);
            }
        });

        //排序
        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD = reverseJavaRDD.sortByKey(false).mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            public Tuple2<String, Integer> call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                return new Tuple2<String, Integer>(integerStringTuple2._2, integerStringTuple2._1);
            }
        });

        //收集结果数据
        List<Tuple2<String, Integer>> finalResult = stringIntegerJavaPairRDD.collect();

        //打印数据
        for (Tuple2<String, Integer> tuple : finalResult) {
            System.out.println(tuple._1+"出现的次数"+tuple._2);
        }

        //关闭
        sc.stop();



    }
}

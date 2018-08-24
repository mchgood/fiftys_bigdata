package cn.fiftys.spark.core.example

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 利用scala语言实现wordcount程序
  */
object WordCount {
  def main(args: Array[String]): Unit = {
    //设置spark的配置文件信息
    val sparkConf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    //构建sparkContext上下文对象,他是程序的入口,所有计算的源头
    val sc = new SparkContext(sparkConf)
    //设置日志输出级别
    sc.setLogLevel("WARN")
    //读取文件
    val file: RDD[String] = sc.textFile("D:\\file1.txt")
    //对文件中每一行单词进行压平切分
    val words: RDD[String] = file.flatMap(_.split(" "))
    //对每一个单词计数为1,转化为(单词,1)格式
    val wordAndOne: RDD[(String, Int)] = words.map(x => (x, 1))
    //相同的单词进行累加汇总, 前一个下划线表示累加数据,后一个下划线表示新数据
    val result: RDD[(String, Int)] = wordAndOne.reduceByKey(_ + _)
    //将输出结果按照单词出现的次数降序排列
    val sortResult: RDD[(String, Int)] = result.sortBy(_._2,false)
    //收集结果数据
     val finalResult: Array[(String, Int)] = sortResult.collect()
    //打印结果集数据
    finalResult.foreach(x =>println(x))
    //关闭
    sc.stop()
  }
}

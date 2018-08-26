package cn.fiftys.spark.core.example

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TransformationReduceByKey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("TransformationReduceByKey")
      .setMaster("local")
    val sc = new SparkContext(conf)

    val scoreList = Array(Tuple2("class1", 80), Tuple2("class2", 75),
      Tuple2("class1", 90), Tuple2("class2", 60))

    val scores: RDD[(String, Int)] = sc.parallelize(scoreList)
    val reduceRDD: RDD[(String, Int)] = scores.reduceByKey(_+_)
    reduceRDD.foreach(x => println(x._1+":"+x._2))
  }

}

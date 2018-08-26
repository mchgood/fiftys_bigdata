package cn.fiftys.spark.core.example

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TransformationSortByKey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("TransformationSortByKey")
      .setMaster("local")
    val sc = new SparkContext(conf)

    val scoreList = Array(Tuple2(65, "leo"), Tuple2(50, "tom"),
      Tuple2(100, "marry"), Tuple2(85, "jack"))

    val scores: RDD[(Int, String)] = sc.parallelize(scoreList,1)

    val sortedScores: RDD[(Int, String)] = scores.sortByKey(false)

    sortedScores.foreach(x => println(x._1+":"+x._2))


  }

}

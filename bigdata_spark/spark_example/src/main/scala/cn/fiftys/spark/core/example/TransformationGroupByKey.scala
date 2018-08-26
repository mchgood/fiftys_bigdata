package cn.fiftys.spark.core.example

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TransformationGroupByKey {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("TransformationGroupByKey")
      .setMaster("local")

    val sc = new SparkContext(conf)

    val scoreList = Array(
      Tuple2("class1", 80),
      Tuple2("class2", 90),
      Tuple2("class1", 75),
      Tuple2("class2", 95))

    val scores: RDD[(String, Int)] = sc.parallelize(scoreList, 1)
    val groupScores = scores.groupByKey()

    groupScores.foreach(score => {
      println(score._1)
      score._2.foreach { x => println(x) }
      println("===============")
    })


  }
}

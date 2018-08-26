package cn.fiftys.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

object TransformationJoin {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("TransformationJoin")
      .setMaster("local")
    val sc = new SparkContext(conf)

    val studentList = Array(
      Tuple2(1, "leo"),
      Tuple2(2, "jack"),
      Tuple2(3, "tom"))

    val scoreList = Array(
      Tuple2(1, 100),
      Tuple2(2, 90),
      Tuple2(3, 60))

    val students = sc.parallelize(studentList)
    val scores = sc.parallelize(scoreList)

    val studentScores = students.join(scores)

    studentScores.foreach(studentScore => {
      println("student id: " + studentScore._1)
      println("student name: " + studentScore._2._1)
      println("student socre: " + studentScore._2._2)
      println("=======================================")
    })
  }
}

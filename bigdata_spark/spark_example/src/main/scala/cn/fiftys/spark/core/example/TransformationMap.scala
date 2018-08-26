package cn.fiftys.spark.core.example

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TransformationMap {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName("TransformationMap")
      .setMaster("local")
    val sc = new SparkContext(conf)

    val number = Array(1, 2, 3, 4, 5)

    val numberRDD: RDD[Int] = sc.parallelize(number)

    val mulitNumberRDD: RDD[Int] = numberRDD.map(x => x * 2)

    mulitNumberRDD.foreach(x => print(x))


  }

}

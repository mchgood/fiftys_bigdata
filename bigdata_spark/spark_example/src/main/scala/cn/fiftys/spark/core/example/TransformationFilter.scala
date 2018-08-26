package cn.fiftys.spark.core.example

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TransformationFilter {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("TransformationFilter").setMaster("local")
    val sc = new SparkContext(conf)

    val number = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val numberRDD: RDD[Int] = sc.parallelize(number,1)
    val filterNumberRDD: RDD[Int] = numberRDD.filter(x => x % 2 == 0)
    filterNumberRDD.foreach(x => println(x))
  }
}

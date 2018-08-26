package cn.fiftys.spark.core.example

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TransformationFlatMap {
  def main(args: Array[String]): Unit = {
    val conf =  new SparkConf()
      .setAppName("TransformationFlatMap")
      .setMaster("local")
    val sc = new SparkContext(conf)

   val stringNumber =  Array(
     "hello word hello spark hello hadoop",
     "my mama dont like you")

    val stringNumberRDD: RDD[String] = sc.parallelize(
      stringNumber,1)

    val spiltNumber: RDD[String] = stringNumberRDD.flatMap(
      x => x.split(" "))

    spiltNumber.foreach(x => println(x))
  }

}

package cn.fiftys.spark.weblog

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object UV {
  def main(args: Array[String]): Unit = {
    //创建sparkConf
    val conf: SparkConf = new SparkConf().setAppName("UV").setMaster("local[2]")
    //创建sparkContext对象
    val sc = new SparkContext(conf)
    //读取文件
    val text: RDD[String] = sc.textFile("D:\\mappertest\\access.log")
    //切分每一行获取到ip
    val ips: RDD[String] = text.map(_.split(" ")(0))
    //根据ip地址去重
    val distinct: RDD[String] = ips.distinct()
    //获取到uv总量
    val num: Long = distinct.count()
    println("UV:"+num)
    sc.stop()

  }


}

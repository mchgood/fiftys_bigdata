package cn.fiftys.spark.weblog

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 通过spark实现点击流日志分析案例 ---PV
  */
object PV {
  def main(args: Array[String]): Unit = {
    //创建sparkConf对象
    val conf: SparkConf = new SparkConf().setAppName("PV").setMaster("local[2]")
    //创建sparkContext上下文对象
    val sc = new SparkContext(conf)
    //读取本地数据
    val text: RDD[String] = sc.textFile("D:\\mappertest\\access.log")
    //获取pv总量
    println(text.count())
    sc.stop()



    /*//将一行数据作为输出,输出(pv,1)
    val pvAndOne: RDD[(String, Int)] = text.map(x => ("pv", 1))
    //聚合输出
    val totalPV: RDD[(String, Int)] = pvAndOne.reduceByKey(_ + _)
    totalPV.foreach(print)
    sc.stop()*/

  }

}

package cn.fiftys.scala.cases

import scala.util.Random

/**
  * 匹配字符串
  */
object Cases1 {
  def main(args: Array[String]): Unit = {
    val arr = Array("hadoop", "spark", "hive", "zookeeper")
    val name = arr(Random.nextInt(arr.length))
    println(name)
    name match {
      case "hadoop" => println("大数据分布式存储和计算框架.....")
      case "spark"  =>println("大数据分布式内存计算框架")
      case "hive" =>println("大数据分布式数据仓库")
      case "zookeeper" => println("大数据分布式协调服务框架")
      case  _ =>println("不提供该服务")
    }
  }

}

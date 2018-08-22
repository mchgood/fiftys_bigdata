package cn.fiftys.scala.cases

import scala.util.Random

/**
  * 匹配类型
  */
object Cases2 {
  def main(args: Array[String]): Unit = {
    val arr = Array("Hadoop", 1, 2.0, Cases1)
    val v = arr(Random.nextInt(4))
    println(v)
    v match {
      case x: Int => println("Int" + x)
      case y: Double if (y >= 0) =>println("Double" + y)
      case z:String =>println("String" + z)
      case _ =>throw new RuntimeException("not match execption")
    }
  }

}

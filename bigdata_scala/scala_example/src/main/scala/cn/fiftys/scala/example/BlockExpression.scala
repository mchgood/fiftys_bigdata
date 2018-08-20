package cn.fiftys.scala.example

/**
  * 块表达式
  */
object BlockExpression {

  def main(args: Array[String]) {
    val a = 10
    val b = 20
    //在 scala 中{}中包含一系列表达式，块中最后一个表达式的值就是块的值
    val result = {
      val c = a - b
      val d = b - c
      d
    }
    println(result)
  }

}

package cn.fiftys.scala.example

/**
  * 循环语句
  */
object CirculatingToFor {
  def main(args: Array[String]) {
    //for(i <- 表达式),表达式 1 to 10 返回一个 Range（区间）
    for (i <- 1 to 10)
      println("i:" + 1)

    //for (i <- 数组)
    val arr = Array("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")
    for (z <- arr)
      println("z:" + z)

    //高级for循环
    for (i <- 1 to 10; j <- 1 to 3 if i != j)
      print((10 * i + j) + " ")

    //fo推倒式:如果for循环的循环体以yield开始,则该循环会构建出一个集合
    val v = for (i <- 1 to 10) yield i+1
    print(v)
  }

}

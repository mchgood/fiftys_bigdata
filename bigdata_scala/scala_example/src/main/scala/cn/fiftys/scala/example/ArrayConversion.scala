package cn.fiftys.scala.example

/**
  * 数组转换以及数组常用算法
  */
object ArrayConversion {
  def main(args: Array[String]): Unit = {
    //定义一个数组
    val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 85, 1, 8, 6, 18, 12, 11, 15)

    println("sum: " + arr.sum)
    println("max: " + arr.max)
    println("min: " + arr.min)
    println("sorted: " + (arr.sorted).toBuffer)

    println("---------------------------")
    //将偶数取出来乘10生成一个新的数组
    val res = for (i <- arr if i % 2 == 0) yield i * 10

    //filter 是过滤，接收一个返回值为 boolean 的函数
    //map 相当于将数组中的每一个元素取出来，应用传进去的函数
    val r = arr.filter(_ % 2 == 0).map(_ * 10)
    println(res.toBuffer)
    println("============================")
    println(r.toBuffer)
  }

}

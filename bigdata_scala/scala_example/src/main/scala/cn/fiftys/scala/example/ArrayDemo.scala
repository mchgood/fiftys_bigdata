package cn.fiftys.scala.example

import scala.collection.mutable.ArrayBuffer

/**
  * 关于数组的一些操作
  */
object ArrayDemo {
  def main(args: Array[String]) {

    //初始化一个长度为8的定长数组,其所有元素均为0
    val arr = new Array[Int](10)
    //toBuffer 会将数组转换长数组缓冲
    println(arr.toBuffer)

    val arr2 = Array("hadoop", "scala", "kafka")
    println(arr2(2))


    //变长数组(数组缓冲)
    val ab = ArrayBuffer[Int]()
    //向数组缓冲的尾部追加元素
    //+=尾部追加数据
    ab += 1
    //追加多个元素
    ab += (2, 3, 4, 5, 6)
    //测试 -= 移除数组缓冲区相同的数值
    // ab -= 7
    //追加一个数组
    ab ++= Array(7, 8)
    //追加一个数组缓冲
    ab ++= ArrayBuffer(9, 10)
    //在数组中某个位置插入元素用insert,从某下标插入
    ab.insert(0, -1, -2)
    //删除数组中某个位置元素用remove,按照下标删除
    ab.remove(0)
    for (i <- ab) {
      println(i)
    }

    //until 会生成一个 Range
    //reverse 是将前面生成的 Range 反转
    val arr3 = Array(-2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    for (j <- (0 until arr3.length).reverse)
      println(arr3(j))

  }

}

package cn.fiftys.scala.example

/**
  * 拉链操作
  * 使用zip命令可以将多个值绑定在一起
  */
object ZipperOperation {
  def main(args: Array[String]) {
    val names = Array("zhangsan", "lisi", "wangwu")
    val scores = Array(88,85)

   //如果两个数组的元素个数不一致，拉链操作后生成的数组的长度为较小的那个数组的元素个数
    println((names.zip(scores)).toBuffer)

   //如果其中一个元素的个数比较少，可以使用 zipAll 用默认的元素填充
    println((names.zipAll(scores,"wanger",100)).toBuffer)




  }


}

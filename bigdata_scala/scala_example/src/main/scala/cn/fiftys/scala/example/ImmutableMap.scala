package cn.fiftys.scala.example

import scala.collection.immutable._

/**
  * 操作不可变map
  */
object ImmutableMap extends App {

  //定义map集合
  val map = Map("zhangsan" -> 19, "lisi" -> 20, "wanger" -> 21)
  //获取值
  println(map("zhangsan"))
  //通过key获取值,有返回,没有返回默认值
  println(map.getOrElse("waner",111))
  //显示所有的key  ,两种方式 keys  keySet
  println(map.keys)
  println("----------------")
  println(map.keySet)



}

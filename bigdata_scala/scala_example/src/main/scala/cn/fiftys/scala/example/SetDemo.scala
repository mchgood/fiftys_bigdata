package cn.fiftys.scala.example

import scala.collection.immutable._

/**
  * 不可变set
  */
object SetDemo extends App {
  val set = Set(1, 2, 3, 4, 5, 6)
  println(set.sum)
  println(set.min)
  println(set.max)
  println(set.size)

  set + 7

  val set1 = Set(5, 6, 7, 8)
  //两个集合的交集
  println(set & set1)
  //两个集合的并集
  println(set ++ set1)
  //在第一个 set 基础上去掉第二个 set 中存在的元素
  println(set -- set1)
  //返回第一个不同于第二个 set 的元素集合
  println(set &~ set1)
  //计算符合条件的元素个数
  println(set.count(_ > 3))
  //返回第一个不同于第二个的元素集合
  println(set.diff(set1))
  println(set1.diff(set))
  //取子 set(2,5 为元素位置, 从 0 开始，包含头不包含尾)
  println(set.slice(2,4))
  //迭代所有的子 set，取指定的个数组合
  set1.subsets(2).foreach(x =>println(x))


}

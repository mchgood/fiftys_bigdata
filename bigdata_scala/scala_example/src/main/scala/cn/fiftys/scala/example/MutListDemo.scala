package cn.fiftys.scala.example

import scala.collection.mutable.ListBuffer


/**
  * 可变List集合操作
  */
object MutListDemo extends App {
  //构建一个可变列表,初始3个元素
  val lst0 = ListBuffer[Int](1, 2, 3)
  //创建一个空的可变列表
  val lst1 = new ListBuffer[Int]

  //向lst1 中追加元素
  lst1 += 4
  lst1.append(5)

  //将lst1中的元素追加到lst0中,注意没有生成新的集合
  lst0 ++= lst1

  //将lst0和lst1合并成一个新的ListBuffer,注意生成了一个新的集合
  val lst2 = lst0 ++ lst1

  //将元素追加到lst0的后面生成一个新的集合
  val lst3 = lst0 :+ (4)

  //删除元素,
  val lst4 = ListBuffer[Int](1, 2, 3, 4, 5, 6)
  lst4 -= 5
  lst4.remove(1)

  //删除一个集合列表,生成以一个新的集合
  val lst5 = lst4 -- List(1, 2)

  //把可变List转换成不可变的List 直接加上toList
  val lst6 = lst5.toList

  //把可变list 转变数组用toArray
  val lst7 = lst5.toArray

  println(lst0)
  println(lst1)
  println(lst2)
  println(lst3)
  println(lst4)
  println(lst5)
  println(lst6)
  println(lst7.toBuffer)

}

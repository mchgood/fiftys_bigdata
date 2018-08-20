package cn.fiftys.scala.example

import scala.collection.immutable._

/**
  * 不可变List集合操作
  */
object ImmutListDemo {
  def main(args: Array[String]): Unit = {
    //创建一个不可变的集合
    val lst1 = List(1, 2, 3)
    //第二种创建list集合的变法
    val other_list = 2 :: Nil


    //获取集合中的第一个元素
    val first = lst1.head

    //获取集合中除了的第一个元素的其他元素
    //补充,如果List中只有一个元素,那么他的head就是这个元素,他的tail就是Nil
    val tail = lst1.tail

    //把0插入到lst1的前面生成一个新的集合
    val lst2 = 0 :: lst1
    val lst3 = lst1.::(0)
    val lst4 = 0 +: lst1
    val lst5 = lst1.+:(0)

    //将一个元素添加到lst1的后面产生一个新的集合
    val lst6 = lst1 :+ 4
    val lst0 = List(7, 8, 9)

    //将两个list合并成一个新的List
    val lst7 = lst1 ++ lst0
    //将lst0插入到lst前面生成一个新的集合
    val lst8 = lst1 ++: lst0
    //将lst0插入到lst1前面生成一个新的集合
    val lst9 = lst1.:::(lst0)

    println(lst1)
    println(other_list)
    println(first)
    println(tail)
    println(lst2)
    println(lst3)
    println(lst4)
    println(lst5)
    println(lst6)
    println(lst7)
    println(lst8)
    println(lst9)


  }


}

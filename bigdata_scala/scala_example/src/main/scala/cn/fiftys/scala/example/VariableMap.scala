package cn.fiftys.scala.example

import scala.collection.mutable

/**
  * 操作一个可变map集合
  */
object VariableMap extends App {
  //声明一个可变map集合
  val user = mutable.HashMap("zhangsan" -> 50, "lisi" -> 100)
  //添加键值对
  user += ("wangwu" -> 88)
  //添加多个键值对
  user += ("liliu" -> 77, "guotao" -> 99)
  //添加一个map集合
  val user1 = mutable.HashMap("shitou" -> 87)
  user ++= user1

  //显示所有的key  keys
  println(user.keys)
  //显示所有的key  keySet
  println(user.keySet)

  //通过key获取到value
  println(user.getOrElse("shitou", 20))
  println(user("zhangsan"))

  //更新键值对
  user("zhangsan") = 88
  println(user("zhangsan"))

  //更新多个键值对
  user += ("zhangsan" -> 99, "lisi" -> 98)
  println(user("zhangsan"))
  println(user("lisi"))

  /* //删除key
   user -= ("zhangsan")
   user.remove("lisi")*/
  println("--------------------------")
  //遍历map三种方式
  //第一种,获取所有key值
  for (x <- user.keys) println(x + "----->" + user(x))

  println("--------------------------")
  //第二种,模式匹配
  for ((x, y) <- user) println(x + "----->" + y)

  println("--------------------------")
  //第三种,foreach
  user.foreach {
    case (x, y) => println(x + "----->" + y)
  }

}

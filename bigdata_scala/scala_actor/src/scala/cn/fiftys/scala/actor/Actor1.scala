package cn.fiftys.scala.actor

import scala.actors.Actor

/**
  * 第一个例子
  * 1,定义一个 class 或者是 object 继承 Actor 特质，注意导包 import scala.actors.Actor
  * 2、重写对应的 act 方法
  * 3、调用 Actor 的 start 方法执行 Actor
  * 4、当 act 方法执行完成，整个程序运行结束
  */
class Actor1 extends Actor {
  //重写act方法
  override def act(): Unit = {
    for (i <- 1 to 10) {
      println("actor1............" + i)
    }
  }
}

class Actor2 extends Actor {
  override def act(): Unit = {
    for (j <- 2 to 20) {
      println("actor2.........." + j)
    }
  }
}

object MyActor extends App {
  private val actor1 = new Actor1
  private val actor2 = new Actor2
  actor1.start()
  actor2.start()
}
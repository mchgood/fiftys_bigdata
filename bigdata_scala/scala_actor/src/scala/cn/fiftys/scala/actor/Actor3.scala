package cn.fiftys.scala.actor

import scala.actors.Actor

/** 1、定义一个 class 或者是 object 继承 Actor 特质，注意导包 import scala.actors.Actor
  * 2、重写对应的 act 方法
  * 3、调用 Actor 的 start 方法执行 Actor
  * 4、通过不同发送消息的方式对 actor 发送消息
  * 5、act 方法中通过 receive 方法接受消息并进行相应的处理
  * 6、act 方法执行完成之后，程序退出
  */
class Actor3 extends Actor {
  override def act(): Unit = {
    receive {
      case "start" => {
        println("starting.........")
      }
    }
  }
}

object MyActor3{
  def main(args: Array[String]): Unit = {
    val actor = new Actor3
    actor.start()
    actor ! "start"
    println("消息发送成功")
  }
}
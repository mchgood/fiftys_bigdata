package cn.fiftys.scala.actor

import scala.actors.Actor

/**
  * 使用 react 方法代替 receive 方法去接受消息
  * 好处：react 方式会复用线程，避免频繁的线程创建、销毁和切换。比 receive 更高效
  * 注意: react 如果要反复执行消息处理，react 外层要用 loop，不能用 while
  */
class Actor5 extends Actor {
  override def act(): Unit = {
    loop {
      react {
        case "start" => {
          println("接受消息.....")
        }
        case "stop" => {
          println("停止接受消息")
        }
      }
    }
  }
}

object MyActor5 {
  def main(args: Array[String]): Unit = {
    val actor = new Actor5
    actor.start()
    actor ! "start"
    actor ! "stop"
  }
}
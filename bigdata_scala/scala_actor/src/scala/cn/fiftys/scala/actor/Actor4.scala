package cn.fiftys.scala.actor

import scala.actors.Actor

/**
  * 实现actor可以不断的接受消息
  * 在 act 方法中可以使用 while(true)的方式，不断的接受消息。
  *
  * 发送 start 消息和 stop 的消息是异步的，但是 Actor 接收到消息执行的过程是同步
  * 的按顺序执行
  */
class Actor4 extends Actor {
  override def act(): Unit = {
    while (true) {
      receive {
        case "start" => {
          println("starting......")
        }
        case "stop" => {
          println("stopping........")
        }
      }
    }
  }
}

object MyActor4 extends App {
  private val actor = new Actor4
  actor.start()
  actor ! "start"
  actor ! "stop"
}

package cn.fiftys.scala.actor

import scala.actors.{Actor, Future}

/**
  * 结合 case class 样例类发送消息和接受消息
  *
  * 1、将消息封装在一个样例类中
  * 2、通过匹配不同的样例类去执行不同的操作
  * 3、Actor 可以返回消息给发送方。通过 sender 方法向当前消息发送方返回消息
  */
case class SyncMessage(id: Int, msg: String) //同步消息
case class AsyncMessage(id: Int, msg: String) //异步消息
case class ReplyMessage(id: Int, msg: String) //返回结果消息
class Actor6 extends Actor {
  override def act(): Unit = {
    loop {
      react {
        case "start" => {
          println("starting......")
        }
        case SyncMessage(id, msg) => {
          println(s"id:$id,SyncMessage:$msg")
          sender ! ReplyMessage(1, "同步消息............")
        }
        case AsyncMessage(id, msg) => {
          println(s"id:$id,AsyncMessage:$msg")
          sender ! ReplyMessage(2, "异步消息.............")
        }
      }
    }
  }
}

object MyActor6 {
  def main(args: Array[String]): Unit = {
    val actor = new Actor6
    actor.start()
    actor ! "start"

    //同步消息,有返回值
    val reply1: Any = actor !? SyncMessage(1, "我是同步消息")
    println(reply1)
    println("==================")

    val reply2: Unit = actor ! AsyncMessage(2,"我是异步没有返回值消息")
    val reply3: Future[Any] = actor !! AsyncMessage(3,"我是异步有返回值消息")
    //Future 的 apply()方法会构建一个异步操作且在未来某一个时刻返回一 个值
    val result: Any = reply3.apply()
    println(result)
  }
}
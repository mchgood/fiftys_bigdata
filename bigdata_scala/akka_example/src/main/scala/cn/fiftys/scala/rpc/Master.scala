package cn.fiftys.scala.rpc

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}


/**
  * 利用akka的actor模型实现两个进程间的通信--master端
  */
class Master extends Actor {

  println("master端的构造代码块首先执行....")

  //preStart方法会在构造代码块执行之后执行,并且指挥调用一次
  override def preStart(): Unit = {
    println("master端preStart方法执行.....")

  }

  //receive方法会在preStart方法执行后被调用,并且会不断的接受消息
  override def receive: Receive = {
    case "connect" => {
      println("接受connect消息成功.....")
    }
      //master发送注册成功信息给worker
      sender ! "success"
      println("注册成功返回数据给worker")
  }
}


object Master {
  def main(args: Array[String]): Unit = {
    //master的ip地址
    val host = args(0)
    //master的port
    val port = args(1)

    //准备配置信息
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
      """.stripMargin
    //配置config对象,利用ConfigFactory解析配置文件,获取配置信息
    val config = ConfigFactory.parseString(configStr)
    //1,创建ActorSystem,他是整个进程中的老大,负责,监督和创建actor,他是一个单例对象
    val masterActorSystem = ActorSystem("masterActorSystem", config)
    //2,通过ActorySystem来创建master actory
    val masterActor: ActorRef = masterActorSystem.actorOf(Props(new Master), "masterActor")

    //3,向master actor发送消息
    //masterActor ! "connect"
  }
}
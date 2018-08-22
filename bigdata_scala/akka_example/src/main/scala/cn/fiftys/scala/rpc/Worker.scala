package cn.fiftys.scala.rpc

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

/**
  * 利用akka的actor模型实现两个进程间的通信--worker端
  */
class Worker extends Actor {

  //构造代码块
  println("worker端的构造代码块....")

  override def preStart(): Unit = {
    println("worker端的preStart方法执行......")
    //获取 master actor 的引用
    //ActorContext 全局变量，可以通过在已经存在的 actor 中，寻找目标 actor
    //调用对应 actorSelection 方法，
    // 方法需要一个 path 路径：1、通信协议、2、master 的 IP 地址、3、master 的端口 4、创建 master actor 老大 5、actor 层级
    val master: ActorSelection = context.actorSelection("akka.tcp://masterActorSystem@192.168.137.1:8888/user/masterActor")
    //向master发送消息
    master ! "connect"
  }

  override def receive: Receive = {
    case "connect" => {
      println("匹配消息成功connect")
    }
    case "success" => {
      println("注册成功")

    }
  }
}

object Worker {
  def main(args: Array[String]): Unit = {
    //定义host
    val host = args(0)
    val port = args(1)

    //准备配置文件
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
      """.stripMargin
    //通过CongifFactory来解析配置信息
    val config: Config = ConfigFactory.parseString(configStr)
    //1,创建ActorSystem,他是整个进程中的老大,负责,监督和创建actor,他是一个单例对象
    val workActorSystem = ActorSystem("workActorSystem", config)
    //2,通过 actorSystem 来创建 worker actor
    val workerActor: ActorRef = workActorSystem.actorOf(Props(new Worker), "workerActor")
    //向work actor发送消息
    // workerActor ! "connect"
  }
}

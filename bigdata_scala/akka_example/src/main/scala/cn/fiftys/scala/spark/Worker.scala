package cn.fiftys.scala.spark

import java.util.UUID

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import scala.concurrent.duration._

class Worker(val memory: Int, val cores: Int, val masterHost: String, val masterPort: String) extends Actor {

  //worker的构造代码块
  println("worker的构造代码块....")

  //定义workerId
  private val WorkerId: String = UUID.randomUUID().toString

  //定义发送心跳的事件间隔
  val SEND_HEART_HEAT_INTERVAL = 10000 //10秒

  //定义全局变量
  var master: ActorSelection = _

  //preStart方法会在构造代码块之后被调用,并且指挥被调用一次
  override def preStart(): Unit = {
    println("preStart方法执行...........")
    //获取maseter的引用
    //ActorContext 全局变量，可以通过在已经存在的actor 中，寻找目标actor
    //调用对应actorSelection 方法，
    // 方法需要一个path 路径：1、通信协议、2、master 的IP 地址、3、master 的端口4、创建masteractor 老大5、actor 层级
    master = context.actorSelection(s"akka.tcp://masterActorSystem@$masterHost:$masterPort/user/masterActor")

    //向master发送注册信息,将信息封装在样例类中,主要包含参数:workerId,memory,cores
    master ! RegisterMessage(WorkerId, memory, cores)
  }

  override def receive: Receive = {
    //worker接收master的反馈信息
    case RegisteredMessage(message) => {
      println(message)

      //向master定期的发送心跳
      //worker先给自己发送心跳
      //schedule方法中4个参数:1,
      import context.dispatcher
      context.system.scheduler.schedule(0 millis, SEND_HEART_HEAT_INTERVAL millis, self, HeartBeat)
    }
    //worker接收心跳
    case HeartBeat => {
      //此时worker才是真正向master发送心跳
      master ! SendHeartBeat(WorkerId)
    }

  }
}

object Worker {
  def main(args: Array[String]): Unit = {
    //定义worker的Ip地址
    val host = args(0)
    //定义worker的端口
    val port = args(1)
    //定义worker的内存
    val memory = args(2).toInt
    //定义worker的核数
    val cores = args(3).toInt
    //定义master的ip地址
    val masterHost = args(4)
    //定义master的端口
    val masterPort = args(5)

    //准备配置文件
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
        """.stripMargin
    //通过configFactory来解析配置信息
    val config: Config = ConfigFactory.parseString(configStr)
    //1,创建ActorSystem,它是整个进程中的老大,它负责创建和监督actor
    val workerActorSystem = ActorSystem.apply("workerActorSystem", config)
    //2,通过actorySystem来创建worker actor
    val workerActor: ActorRef = workerActorSystem.actorOf(Props(new Worker(memory, cores, masterHost, masterPort)), "workerActor")

  }

}
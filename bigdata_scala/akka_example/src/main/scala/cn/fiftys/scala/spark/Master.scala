package cn.fiftys.scala.spark

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._

class Master extends Actor {

  //构造代码块先执行
  println("构造代码块先执行......")

  //定义一个map集合,用于存放worker信息
  private val workerMap = new mutable.HashMap[String, WorkerInfo]()
  //定义一个List集合,用于存放WorkerInfo信息,方便后期按照worker上的资源进行排序
  private val workerList = new ListBuffer[WorkerInfo]
  //master顶起检查的时间间隔
  val CHECK_OUT_TIME_INTERVAL = 15000 //15秒

  override def preStart(): Unit = {
    println("preStart方法会在构造代码块之后执行.....")
    //master定期检查超时的worker
    //需要手动导入隐式转换
    import context.dispatcher
    context.system.scheduler.schedule(0 millis, CHECK_OUT_TIME_INTERVAL millis, self, CheckOutTime)
  }

  override def receive: Receive = {

    //master接受worker的注册信息
    case RegisterMessage(workerId, memory, cores) => {
      //判断当前的worker是否已经注册
      if (!workerMap.contains(workerId)) {
        //保存信息到map集合中
        val workerInfo = new WorkerInfo(workerId, memory, cores)
        workerMap.put(workerId, workerInfo)
        //保存workerInfo到list集合中
        workerList += workerInfo
        //master 反馈注册成功给worker
        sender ! RegisteredMessage(s"workerId:$workerId 注册成功")
      }
    }

    //master接收worker的心跳信息
    case SendHeartBeat(workerId) => {
      //判断worker是否已经注册,master只接受已经注册过的worker的心跳信息
      if (workerMap.contains(workerId)) {
        //获取workerinfo
        val workerInfo = workerMap(workerId)
        //获取当前系统时间
        val lastTime: Long = System.currentTimeMillis()
        workerInfo.lastHeartBeatTime = lastTime
      }
    }
    case CheckOutTime => {
      //过滤出超时worker判断逻辑:获取当前系统时间 -worker上一次心跳时间>master定时检查的时间间隔
      val outTimeWorkers: ListBuffer[WorkerInfo] = workerList.filter(x => System.currentTimeMillis() - x.lastHeartBeatTime > CHECK_OUT_TIME_INTERVAL)
      //遍历超时的worker信息,然后移除掉超时的worker
      for (workerinfo <- outTimeWorkers) {
        //获取到workerId
        val workeId: String = workerinfo.workerId
        //从map集合中移除掉超时的worker信息
        workerMap.remove(workeId)
        //从list集合中移除超时的workerInfo信息
        workerList -= workerinfo
        println("超时的workerId:" + workeId)
      }
      println("正在运行的worker总数:" + workerList.size)
      //master按照worker内存大小进行降序排列
      println(workerList.sortBy(x => x.memory).reverse.toList)
    }
  }
}

object Master {
  def main(args: Array[String]): Unit = {
    //master的ip
    val host = args(0)
    //master的端口号
    val port = args(1)
    //准备配置信息
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin

    //配置config对象 利用ConfigFactory解析配置文件,获取配置信息
    val config: Config = ConfigFactory.parseString(configStr)
    //创建ActorSystem,他是整个进程中的老大,它负责创建和监督actor.单利对象
    val masterActorSystem = ActorSystem("masterActorSystem",config)
    //通过ActorSytem来创建master actor
    val masterActor: ActorRef = masterActorSystem.actorOf(Props(new Master),"masterActor")

  }
}
package cn.fiftys.scala.spark

//封装worker信息
class WorkerInfo(val workerId: String, val memory: Int, val cores: Int) {
  //定义一个变量用于存放worker上一次发送心跳的时间
  var lastHeartBeatTime: Long = _

  //重写toString方法
  override def toString: String = {
    s"workerId:$workerId,memory:$memory,cores:$cores"
  }

}

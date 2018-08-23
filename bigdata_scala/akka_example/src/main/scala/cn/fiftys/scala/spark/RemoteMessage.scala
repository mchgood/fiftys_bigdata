package cn.fiftys.scala.spark


trait RemoteMessage extends Serializable {

}

//worker向master发送注册信息,由于不再统一进程中,需要实现序列化
case class RegisterMessage(val workerId: String, val memory: Int, val cores: Int) extends RemoteMessage

//master反馈注册成功信息给worker,由于不再统一进程中,也需要实现序列化
case class RegisteredMessage(message:String) extends RemoteMessage

//worker向自己发送心跳,在同一进程中不需要实现序列化
case object HeartBeat

//worker向master发送心跳,由于不再同一进程,所以需要实现序列化
case class SendHeartBeat(val workerId:String) extends RemoteMessage

//master自己向自己定时发送心跳,在同一进程中,不需要实现序列化
case object CheckOutTime
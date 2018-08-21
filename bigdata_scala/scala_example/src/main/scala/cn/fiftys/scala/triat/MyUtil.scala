package cn.fiftys.scala.triat

/**
  * trait 继承class
  * 在 Scala 中 trait 也可以继承 class，此时这个 class 就会成为所有继承
  * 该 trait 的子类的超级父类
  */
class MyUtil {
  def printMsg(msg :String) = println(msg)
}

trait Logger_Two extends MyUtil{
  def log(msg:String) = this.printMsg("log-->: "+msg)
}

class Person_Three(val name:String) extends Logger_Two{
  def sayHello: Unit ={
     this.log("hi, i am " + this.name)
     this.printMsg("hello, i am " + this.name)
  }
}
object Person_Three{
  def main(args: Array[String]): Unit = {
    val p = new Person_Three("tom")
    p.sayHello
  }
}
package cn.fiftys.scala.triat


/**
  * 在 Trait中定义具体的方法
  *
  * Scala中的trait不仅可以定义抽象方法，还可以定义具体的方法，此时 trait 更
  * 像是包含了通用方法的工具，可以认为 trait 还包含了类的功能。
  */
trait Logger {
  def log(message: String): Unit = println(message)
}

class PersonForLog(val name: String) extends Logger {
  def makeFriends(other: PersonForLog) = {
    println("Hello, " + other.name + "! My name is " + this.name + ", I miss you!!")
    this.log("makeFriends method is invoked with parameter PersonForLog[name = " + other.name + "]")
  }
}

object PersonForLog {
  def main(args: Array[String]): Unit = {
    val p1 = new PersonForLog("tom")
    val p2 = new PersonForLog("yummy")
    p1.makeFriends(p2)
  }
}
package cn.fiftys.scala.class_extends

/**
  * scala中的protected
  */
class Person4 {
  protected var name: String = "tom"
  protected[this] var hobby: String = "music"

  protected def sayBye = println("再见...........")
}

class Student4 extends Person4 {
  //父类中使用protected修饰的field可以直接访问
  def sayHello = println("hello:" + name)

  //父类中使用protected修饰的method可以直接访问的
  def sayByeBye = sayBye

  def makeFriends(s: Student4) = {
    println("My Hobby is " + hobby + ",your hobby is why")
  }
}

object Student4 {
  def main(args: Array[String]): Unit = {
    val s: Student4 = new Student4
    s.sayHello
    s.makeFriends(s)
    s.sayByeBye
  }
}
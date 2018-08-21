package cn.fiftys.scala.triat

/**
  * 在trait中定义抽象field
  *  Scala 中的 trait 也能定义抽象 field， 而 trait 中的具体方法也能基于抽象 field 编写；
  *  继承 trait 的类，则必须覆盖抽象 field，提供具体的值；
  */
trait SayHelloTrait {
  val msg: String

  def sayHello(name: String) = println(msg + ", " + name)
}

class PersonForAbstractField(val name:String) extends SayHelloTrait{
    //必须覆盖抽象field
   val  msg = "hello"
  def makeFriends(other:PersonForAbstractField) = {
     this.sayHello(other.name)
    println("I'm " + this.name + ", I want to make friends with you!!")
  }
}
object PersonForAbstractField{
  def main(args: Array[String]): Unit = {
    val p1 = new PersonForAbstractField("tom")
    val p2 = new PersonForAbstractField("yummy")

    p1.makeFriends(p2)
  }
}
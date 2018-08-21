package cn.fiftys.scala.triat

/**
  * 在实例对象中混入某个trait
  *  可在创建类的对象时，为该对象指定混入某个 trait，且只有混入了 trait 的对
  * 象才具有 trait 中的方法，而其他该类的对象则没有；
  *  在创建对象时，使用 with 关键字指定混入某个 trait；
  */
trait LoggedTrait {
  //该方法为实现的具体的方法
  def log(msg: String) = {}
}

trait MyLogger extends LoggedTrait {
  //覆盖掉log()方法
  override def log(msg: String): Unit = println("log: " + msg)
}

class PersonForMixTraitMethod(val name: String) extends LoggedTrait {
  def sayHello = {
    println(" hi, my name" + this.name)
    log("一人做事一人当,小叮做事小叮当")
  }
}

object PersonForMixTraitMethod{
  def main(args: Array[String]): Unit = {
    val p1 = new PersonForMixTraitMethod("tom") with MyLogger
    val p2 = new PersonForMixTraitMethod("yummy")

    p1.sayHello
    p2.sayHello
  }
}
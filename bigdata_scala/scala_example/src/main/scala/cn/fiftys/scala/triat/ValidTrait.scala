package cn.fiftys.scala.triat

/**
  * 混合使用trait 的具体方法和抽象方法
  * 在 trait 中，可以混合使用具体方法和抽象方法；
  *  可以让具体方法依赖于抽象方法，而抽象方法则可放到继承 trait 的子类中
  * 去实现；
  *  这种 trait 特性，其实就是设计模式中的模板设计模式的体现；
  */
trait ValidTrait {
  //定义一个抽象方法
  def getName: String
  //定义具体方法
  def valid :Boolean = {"Tom".equals(this.getName) }
}

class PersonForValid(val name:String) extends ValidTrait{
  override def getName: String = this.name
}

object PersonForValid{
  def main(args: Array[String]): Unit = {
    val p = new PersonForValid("Tom")
    println(p.valid)
  }
}

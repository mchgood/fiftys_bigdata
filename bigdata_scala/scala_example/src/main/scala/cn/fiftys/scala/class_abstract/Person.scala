package cn.fiftys.scala.class_abstract

/**
  * Scala中的抽象类
  *
  * 如果在父类中，有某些方法无法立即实现，而需要依赖不同的子类来覆盖，重写实现
  * 不同的方法。此时，可以将父类中的这些方法编写成只含有方法签名，不含方法体的
  * 形式，这种形式就叫做抽象方法;
  *  一个类中，如果含有一个抽象方法或抽象 field，就必须使用 abstract 将类声明为抽
  * 象类，该类是不可以被实例化的;
  *  在子类中覆盖抽象类的抽象方法时，可以不加 override 关键字;
  */
abstract class Person(val name: String) {
  //必须指定返回值类型,不然默认返回Unit
  def sayHello: String

  def sayBay: String

}
class Student(name:String) extends Person(name) {
  override def sayHello: String = "Hello," +name

  override def sayBay: String = "bey ," +name
}

object Student{
  def main(args: Array[String]): Unit = {
    val  s  =new Student("yummy")
    println(s.sayBay)
    println(s.sayHello)
  }
}

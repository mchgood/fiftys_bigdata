package cn.fiftys.scala.class_extends

/**
  * scala中的继承
  */
class Person {
  val name = "supper"

  def getName = this.name
}

class Student extends Person {
  //重写Persn中的字段
  override val name = "sub"
  //子类可以自己定义自己的field与method
  val age = 18

  def getAge = this.age
}

object Student {
  def main(args: Array[String]): Unit = {
    val stu = new Student
    println(stu.age)
  }
}

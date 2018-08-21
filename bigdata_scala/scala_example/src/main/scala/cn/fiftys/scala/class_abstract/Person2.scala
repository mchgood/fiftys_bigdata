package cn.fiftys.scala.class_abstract

/**
  * 如果在父类中，定义了 field，但是没有给出初始值，则此 field 为抽象 field；
  * @param name
  */
abstract class Person2(val name:String) {
  val age: Int
}

class Student(name:String) extends Person2(name){
  override val age: Int = 50
}
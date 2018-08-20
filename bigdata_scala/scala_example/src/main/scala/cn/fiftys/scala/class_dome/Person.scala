package cn.fiftys.scala.class_dome

/**
  * 在scala中,类并不用声明为public类型
  * Scala源文件中可以包含多个类,所有的这些类都是具有共见性
  */
class Person {

  //用val修饰的变量是可读性,类似于java中的提供了get方法,但是没有set方法
  val id = "12345"

  //用val修饰的变量是具有可读可写性的,类似于java中提供了set,get方法
  var age = 18

  //类私有字段，只能在类的内部使用或者伴生对象中访问
  private var name: String = "鬼切"

  //类私有字段，访问权限更加严格的，该字段在当前类中被访问
  //在伴生对象里面也不可以访问
  private[this] var pet = "玉藻前"
}

object Person extends App {
  val p = new Person
  println(p.id)
  p.age = 22
  println(p.age)
  println(p.name)
}

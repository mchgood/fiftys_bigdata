package cn.fiftys.scala.class_extends

/**
  * Scala中override和super关键字
  * 1,Scala 中，如果子类要覆盖父类中的一个非抽象方法，必须要使用 override 关键字；
  * 子类可以覆盖父类的 val 修饰的 field，只要在子类中使用 override 关键字即可。
  * 2,此外，在子类覆盖父类方法后，如果在子类中要调用父类中被覆盖的方法，则必须要使
  * 用 super 关键字，显示的指出要调用的父类方法。
  */
class Person1 {
  private val name = "leo"
  val age = 50

  def getName = this.name
}

class Student1 extends Person1 {
  private val score = "A"
  //子类可以覆盖父类的val Field,使用override关键字
  override val age: Int = 60

  def getScore = this.score

  //覆盖父类非抽象方法,必须使用override关键字
  //同时调用父类中的方法,使用supper关键字
  override def getName: String = "you name is " + super.getName

}

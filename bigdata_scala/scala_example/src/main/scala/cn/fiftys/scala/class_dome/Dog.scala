package cn.fiftys.scala.class_dome

/**
  * Scala 中的伴生对象
  * 如果有一个 class 文件，还有一个与 class 同名的 object 文件，那么就称这个 object
  * 是 class 的伴生对象，class 是 object 的伴生类；
  * 伴生类和伴生对象必须存放在一个.scala 文件中；
  * 伴生类和伴生对象的最大特点是，可以相互访问；
  */
class Dog {
  val id =  1
  private var name = "阿财"
  def printName():Unit={
    //在dog类中可以访问伴生对象中的私有属性
    println(Dog.CONSTANT + name)
  }

}
//伴生对象
object Dog{
  //伴生对象中的私有属性
  private val CONSTANT="小狗叫~汪:-->"

  def main(args: Array[String]): Unit = {
    val  d = new Dog
    //访问私有字段
    d.name = "大黄"
    d.printName()

  }
}

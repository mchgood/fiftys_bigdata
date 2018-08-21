package cn.fiftys.scala.class_extends

/**
  * Scala中getClass 和 classOf
  * isInstanceOf 只能判断出对象是否为指定类以及其子类的对象，而不能精确的判断出，
  * 对象就是指定类的对象；
  *  如果要求精确地判断出对象就是指定类的对象，那么就只能使用 getClass 和 classOf
  * 了；
  *  p.getClass 可以精确地获取对象的类，classOf[XX] 可以精确的获取类，然后使用 ==
  * 操作符即可判断
  */
class Person3 {

}

class Student3 extends Person3

object Student3 {
  def main(args: Array[String]): Unit = {
    val p: Person3 = new Student3
    //判断p是否为person3类的实例
    println(p.isInstanceOf[Person3])
    //判断p的类型是否为Person3类
    println(p.getClass == classOf[Person3])
    //判断p的类型是否为Student4类
    println(p.getClass == classOf[Student3])

  }
}

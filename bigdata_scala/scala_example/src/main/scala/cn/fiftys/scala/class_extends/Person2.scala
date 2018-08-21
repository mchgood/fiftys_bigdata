package cn.fiftys.scala.class_extends

/**
  * Scala中isInstanceOf 和asInstanceOf
  * 首先，需要使用 isInstanceOf 判断对象是否为指定类的对象，如果是的话，则可以
  * 使用 asInstanceOf 将对象转换为指定类型；
  *  注意： p.isInstanceOf[XX] 判断 p 是否为 XX 对象的实例；p.asInstanceOf[XX] 把 p
  * 转换成 XX 对象的实例
  *  注意：如果没有用 isInstanceOf 先判断对象是否为指定类的实例，就直接用
  * asInstanceOf 转换，则可能会抛出异常；
  *  注意：如果对象是 null，则 isInstanceOf 一定返回 false， asInstanceOf 一定返回
  * null；
  */
class Person2 {}

class Student2 extends Person2

object Student2 {
  def main(args: Array[String]): Unit = {
    val p: Person2 = new Student2
    var s: Student2 = null
    //如果对象是 null，则 isInstanceOf 一定返回 false
    println(s.isInstanceOf[Student2])
    //判断P是否是Student2对象的实例
    if (p.isInstanceOf[Student2]) {
      //把p转换为Student2 对象的实例
      s = p.asInstanceOf[Student2]
    }
    println(s.isInstanceOf[Student2])
  }
}

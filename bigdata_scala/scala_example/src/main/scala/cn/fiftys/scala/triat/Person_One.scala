package cn.fiftys.scala.triat

/**
  * trait的构造机制
  *
  *  在 Scala 中，trait 也是有构造代码的，即在 trait 中，不包含在任何方法中的代码；
  *  继承了 trait 的子类，其构造机制如下：
  *  父类的构造函数先执行， class 类必须放在最左边；多个 trait 从左向右依次执行；
  * 构造 trait 时，先构造父 trait，如果多个 trait 继承同一个父 trait，则父 trait 只
  * 会构造一次；所有 trait 构造完毕之后，子类的构造函数最后执行。
  */
class Person_One {
  println("11111111111111111")
}

trait Logger_One {
  println("22222222222222222")
}

trait MyLogger_One extends Logger_One {
  println("33333333333333333")
}

trait TimeLogger_One extends Logger_One {
  println("4444444444444444444")
}

class Student_One extends Person_One with TimeLogger_One with MyLogger_One {
  println("55555555555555555")
}

object exe_one {
  def main(args: Array[String]): Unit = {
    val s = new Student_One

  }
}
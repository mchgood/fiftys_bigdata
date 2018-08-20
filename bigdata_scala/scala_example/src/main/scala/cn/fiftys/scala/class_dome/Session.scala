package cn.fiftys.scala.class_dome

/**
  * object 相当于 class 的单个实例，通常在里面放一些静态的 field 或者 method；
  * 在 Scala 中没有静态方法和静态字段，但是可以使用 object 这个语法结构来达到同样的目的。
  * object 作用：
  *1.存放工具方法和常量
  *2.高效共享单个不可变的实例
  *3.单例模式
  */
class Session {}

object SessionFactory {
  //该部分相当于java中的静态代码块
  val session = new Session

  //在object中的方法相当于java中的静态方法
  def getSession(): Session = {
    session
  }
}

object SingletonDemo {
  def main(args: Array[String]): Unit = {
    //单例模式不需要new关键字创建对象
    val session1 = SessionFactory.getSession()
    println(session1)
    val session2 = SessionFactory.session
    println(session2)

    /**
      * cn.fiftys.scala.class_dome.Session@47f37ef1
      *cn.fiftys.scala.class_dome.Session@47f37ef1
      * 返回的是相同的地址值,表示拿到的是同一个session
      */
  }
}

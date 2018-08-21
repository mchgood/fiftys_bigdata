package cn.fiftys.scala.class_extends

/**
  * scala中的匿名内部类
  *
  * 匿名内部类，就是定义一个没有名称的子类，并直接创建其对象，然后将对象的引用赋
  * 予一个变量，即匿名内部类的实例化对象。然后将该对象传递给其他函数使用。
  */
class Person6(val name: String) {
  def sayHello = "hello ,my name is" + name
}

class GreetDemo {
  //接受person6参数,并规定person6类中只有一个返回String 的sayHello方法
  def greeting(p: Person6 {
    def sayHello: String}) = {
    println(p.sayHello)
  }
}

object GreetDemo {
  def main(args: Array[String]): Unit = {
    val p = new Person6("yummy")
    val g = new GreetDemo
    g.greeting(p)
  }
}
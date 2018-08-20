package cn.fiftys.scala.example

object MethodAndFunction {

  //方法m2参数要求是一个函数,参数类型是两个int类型,返回值类型是int类型
  def m1(f: (Int, Int) => Int): Int = {
    f(2, 6)
  }

  //定义一个函数,参数是两个int类型,返回值也是一个int类型
  val f1 = (x: Int, y: Int) => x + y

  //在定义一个函数f2
  val f2 = (x: Int, y: Int) => x * y

  def main(args: Array[String]): Unit = {
    /*//调用m1方法,并传入f1函数
    println("f1:" + m1(f1))
    println("f2:" + m1(f2))*/

    val f3 = m1 _
    println(f3)
  }

}

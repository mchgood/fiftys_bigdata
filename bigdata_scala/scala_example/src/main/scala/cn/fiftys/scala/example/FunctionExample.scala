package cn.fiftys.scala.example

/**
  * 方法与函数的区别
  */
object FunctionExample {
  def main(args: Array[String]) {

    val f1 = (x: Int, y: Int) => x + y
    print(f1(5, 6))


    def m2(f: (Int, Int) => Int) = f(2, 6)

    val f2 = (x: Int, y: Int) => x - y

    print(m2(f2))

  }

}

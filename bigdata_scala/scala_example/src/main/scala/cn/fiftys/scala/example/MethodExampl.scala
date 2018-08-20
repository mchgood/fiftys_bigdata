package cn.fiftys.scala.example

/**
  * 定义方法
  */
object MethodExampl {
  def main(args: Array[String]) {

    //对于方法的返回值类型可以不必书写,但是如果是递归函数必须指定返回值类型
    def m1(x: Int, y: Int): Int = x * y

    print(m1(5, 6))
    //递归函数
    def m2(x: Int): Int = {
      if (x < 2) 1 else m2(x - 1) * x
    }
    print(m2(3))



  }

}

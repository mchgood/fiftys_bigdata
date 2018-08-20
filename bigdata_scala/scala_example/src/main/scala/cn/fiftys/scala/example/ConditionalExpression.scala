package cn.fiftys.scala.example

/**
  * 条件表示式语句
  */
object ConditionalExpression {
  def main(args: Array[String]) {

    //测试if else语句
    val x = 1
    //判断X的值,将结果赋值给y
    val y = if (x > 1) 2 else 11
    println(y)

    //支持混合类型条件表达式
    val z = if (x > 2) 1 else "张学友"
    println(z)

    //如果缺失else,相当于if(x >2) else ()
    val m = if (x > 2) 1
    println(m)

    val n = if (x > 2) 2 else ()
    println(n)

    //if ,else if 语句
    val k = if (x < 0) 0 else if (x >= 1) 1 else -1
    println(k)



  }

}

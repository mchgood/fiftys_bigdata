package cn.fiftys.scala.example

object TupleDemo {
  def main(args: Array[String]): Unit = {

    val t = ("yummy", 21, 171.5)

    //获取元组中的元素可以使用下划线加上角标.
    //但是需要注意的是元组中的元素角标是从1开始的
    println(t._3)
    println(t._1)

    //toMap可以将对偶的集合转换成映射
    val arr = Array(("tom", 18), ("yummy", 19))
    println(arr.toMap)
    //打印结果:Map(tom -> 18, yummy -> 19)

  }

}

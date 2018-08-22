package cn.fiftys.scala.cases

/**
  * Option类型
  *
  * 在 Scala 中 Option 类型用样例类来表示可能存在或者可能不存在的值(Option 的子类有
  * Some 和 None)。Some 包装了某个值，None 表示没有值
  */
object Cases5 {
  def main(args: Array[String]): Unit = {
    val map = Map("a" -> 1, "b" -> 2)
    val v = map.get("a") match {
      case Some(i) => i
      case None => 0
    }
    println(v)

    //更好的方式
    val v1: Int = map.getOrElse("b",100)
    println(v1)
  }


}

package cn.fiftys.scala.cases

/**
  * 匹配元组,数组,集合
  */
object Cases3 {
  def main(args: Array[String]): Unit = {

    //匹配数组
    val arr = Array(1, 2, 3)
    arr match {
      case Array(1, x, y) => println(x + "," + y)
      case Array(0) => println("Only 0")
      case Array(0, _*) => println("0......")
      case _ => println("else.........")
    }

    //匹配集合
    val list = List(3, -1)
    list match {
      case 0 :: Nil => println("only 0")
      case x :: y :: Nil => println(s"x:$x,y:$y")
      case 0 :: tail => println("0........")
      case _ => println("else.........")
    }

    //匹配元组
    val tup = (1, 3, 7)
    tup match {
      case (_, z, 7) => println(s"z:$z")
      case (1, x, y) => println(s"x:$x,y:$y")
      case _ => println("else.......")
    }
  }
}

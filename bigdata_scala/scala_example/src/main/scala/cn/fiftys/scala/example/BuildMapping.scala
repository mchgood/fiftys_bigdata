package cn.fiftys.scala.example

/**
  * 构建映射格式
  * 在 Scala 中,把哈希表这种数据结构叫做映射
  * 1、val map=Map(键 -> 值，键 -> 值....)
  * 2、利用元组构建 val map=Map((键，值),(键，值),(键，值)....)
  */
object BuildMapping {
  def main(args: Array[String]): Unit = {

   //构建Map
    val map1 = Map("tom" -> 85, "yummy" -> 99, "kitty" -> 100)

    val map2 = Map(("tom", 85), ("yummy", 95), ("kitty", 15))

    //获取值
    println(map1("yummy"))

    //如果映射中有值,返回映射中的值,没有就返回默认值(可自己设定默认值)
    println(map2.getOrElse("yummy",0))

  }

}

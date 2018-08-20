package cn.fiftys.scala.class_dome

/**
  * 构造器
  * 每个类都有主构造器,主构造器的参数直接放置在类名后面,与类交织在一起
  *
  * @param name
  * @param age
  */
class Student(val name: String, val age: Int) {
  //主构造器会执行类定义的所有语句
  println("执行主构造器")

  private var gender = "male"

  def this(name: String, age: Int, gender: String) {
    //每一个辅助构造器必须以主构造器或者其他辅助构造器的调用开始的
    this(name, age)
    println("执行辅助构造器")
    this.gender = gender
  }

}

object Student {
  def main(args: Array[String]): Unit = {
      //val stu1 = new Student("zhangsan", 30)
    val stu2 = new Student("lisi", 18, "yummy")
    println(stu2.gender,stu2.age,stu2.name)
  }
}

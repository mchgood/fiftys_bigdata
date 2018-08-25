package cn.fiftys.spark.sparksql

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}


//定义样例类
case class Student(id: Int, name: String, age: Int)

object RDDToDateFrameReflrction {
  def main(args: Array[String]): Unit = {
    //1,构建sparkSessione appName master地址
    val spark = SparkSession.builder()
      .appName("RDDToDateFrameReflrction")
      .master("local[2]")
      .getOrCreate()
    //2,从sparkSession 获取到sparkContext对象
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")
    //创建dataFrame需要导入隐式转换
    import spark.implicits._

    //3,加载数据
    val studeentDF = sc.textFile("D:\\wordcount\\students.txt")
      //切分
      .map { line => line.split(",") }
      //将Student与RDD关联起来
      .map { arr => Student(arr(0).trim.toInt, arr(1), arr(2).trim.toInt) }
      .toDF()

    //将DataFrame注册成表
    studeentDF.createOrReplaceTempView("students")

    val teenagerDF: DataFrame = spark.sql("select * from students where age < 18")

    spark.sql("select * from students").show()
    val teenagerRDD: RDD[Row] = teenagerDF.rdd

    teenagerRDD.map { row => Student(row(0).toString.toInt, row(1).toString, row(2).toString.toInt) }
      .collect()
      .foreach { stu => println(stu.id + ":" + stu.name + ":" + stu.age) }

    println("==============================")

    //在Scala中使用row的getAs方法,获取指定的列
    teenagerRDD.map { row => Student(row.getAs("id"), row.getAs("name"), row.getAs("age")) }
      .collect()
      .foreach(stu => println(stu.id + ":" + stu.name + ":" + stu.age))

    println("==============================")

    //可以通过row的getValuesMap()方法，获取指定几列的值，返回的是个map
    val studentRDD = teenagerRDD.map { row => {
      val map = row.getValuesMap(Array("id", "name", "age"))
      Student(map("id").toString.toInt, map("name").toString, map("age").toString.toInt)
    }
    }
    studentRDD.collect().foreach(stu => println(stu.id + ":" + stu.name + ":" + stu.age))

  }
}

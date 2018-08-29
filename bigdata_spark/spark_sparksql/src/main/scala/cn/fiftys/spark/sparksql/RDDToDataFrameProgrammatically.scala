package cn.fiftys.spark.sparksql

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object RDDToDataFrameProgrammatically {
  def main(args: Array[String]): Unit = {
    val sqlContext = SparkSession.builder()
      .appName("RDD2DataFrameProgrammatically")
      .master("local")
      .getOrCreate()

    //1,从sqlContext中获取到sparkContext
    val sc = sqlContext.sparkContext
    sc.setLogLevel("WARN")

    val studentRDD = sc.textFile("D:\\wordcount\\students.txt")
      .map { line => Row(line.split(",")(0).toInt, line.split(",")(1), line.split(",")(2).toInt) }

    //2,编程方式动态构造元数据
    val structType = StructType(Array(
      StructField("id", IntegerType, true),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)
    ))

    //3,进行RDD到DataFrame的转换
    val studentDF: DataFrame = sqlContext.createDataFrame(studentRDD, structType)

    //4,注册临时表
    studentDF.createOrReplaceTempView("students")

    //执行sql
    val frame: DataFrame = sqlContext.sql("select * from students where age <30")

    frame.rdd.collect().foreach( row => println(row))


  }
}

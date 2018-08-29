package cn.fiftys.spark.sparksql

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFreamOperation {
  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder
      .appName("DataFreamOperation")
      .master("local[2]")
      .getOrCreate()

    val sc: SparkContext = session.sparkContext
    sc.setLogLevel("WARN")

    val df: DataFrame = session.read.json("D:\\wordcount\\students.json")


    df.show()
    df.printSchema()
    df.select("name").show()
    df.select(df("name"),df("age") +1).show()
    df.filter(df("age") >18).show()
    df.groupBy("age").count().show()
  }
}

package cn.fiftys.spark.sparksql

import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFrameCreate {

  def main(args: Array[String]): Unit = {

    val sparkSql = SparkSession.builder
      .master("local[2]")
      .appName("DataFrameCreate")
      .getOrCreate()

    val df: DataFrame = sparkSql
      .read
      .json("D:\\wordcount\\students.json")

    df.show()
  }
}

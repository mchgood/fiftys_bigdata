package cn.fiftys.spark.sparksql

import org.apache.spark.sql.{Row, SparkSession}

object HiveSupport {
  def main(args: Array[String]): Unit = {
    //创建sparkSession
    val spark = SparkSession.builder()
      .appName("HiveSupport")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")


    //创建学生信息表
    spark.sql("CREATE TABLE IF NOT EXISTS " +
      "student_infos(name string,age int) " +
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ' '")

    //创建学生成绩表
    spark.sql("CREATE TABLE IF NOT EXISTS " +
      "student_scores(name string,score int) " +
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ' '")

    //从本地加载数据到学生信息表
    spark.sql("LOAD DATA LOCAL INPATH " +
      "'D:/ideawork/fiftys_bigdata/bigdata_spark/spark_sparksql/data/student_infos.txt' " +
      "INTO TABLE student_infos")

    //从本地加载数据到学生成绩表中
    spark.sql("LOAD DATA LOCAL INPATH " +
      "'D:/ideawork/fiftys_bigdata/bigdata_spark/spark_sparksql/data/student_scores.txt' " +
      "INTO TABLE student_scores")

    //查询出学生成绩低于90分的
    val goodStudent = spark.sql("SELECT si.name,si.age,ss.score " +
      "FROM student_infos si " +
      "JOIN student_scores ss ON si.name = ss.name " +
      "WHERE ss.score >= 90 ")

    goodStudent.write.saveAsTable("good_student_infos")

    val rows: Array[Row] = spark.table("good_student_infos").collect()

    for (s <- rows) {
      println(s)
    }
  }
}

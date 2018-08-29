package cn.fiftys.spark.sparksql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.List;

public class HiveDataSource {
    public static void main(String[] args) {

        SparkSession hiveContext = SparkSession.builder()
                .appName("HiveDataSource")
                .master("local[2]")
                .enableHiveSupport()
                .getOrCreate();

        hiveContext.sparkContext().setLogLevel("WARN");


        //创建表学生基本信息表
        hiveContext.sql(
                "CREATE TABLE IF NOT EXISTS student_infos(name string,age int)"
                        + "ROW FORMAT DELIMITED FIELDS TERMINATED BY ' '");

        //将学生基本信息数据导入到student_infos表
        hiveContext.sql("LOAD DATA LOCAL INPATH 'D:/ideawork/fiftys_bigdata/bigdata_spark/spark_sparksql/data/student_infos.txt' INTO TABLE student_infos");

        //创建学生成绩表
        hiveContext.sql("CREATE TABLE IF NOT EXISTS student_scores(name string,score int)"
                + "ROW FORMAT DELIMITED FIELDS TERMINATED BY ' '");

        //加载数据到学生成绩表
        hiveContext.sql("LOAD DATA LOCAL INPATH 'D:/ideawork/fiftys_bigdata/bigdata_spark/spark_sparksql/data/student_scores.txt' INTO TABLE student_scores");

        //关联两张表的信息,查询出成绩低于80分的学生信息
        Dataset<Row> goodStudents = hiveContext.sql("SELECT si.name,si.age,ss.score "
                + "FROM student_infos si "
                + "JOIN student_scores ss ON si.name = ss .name "
                + "WHERE ss.score >=90");

        goodStudents.write().saveAsTable("good_student_infos");

       // hiveContext.table("good_student_infos").show();

        Dataset<Row> good_student_infos = hiveContext.table("good_student_infos");

        List<Row> collect = good_student_infos.javaRDD().collect();
        for (Row row : collect) {
            System.out.println(row);
        }

        hiveContext.stop();


    }
}

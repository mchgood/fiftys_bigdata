package cn.fiftys.spark.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

/**
 * 使用json文件创建DateFrame
 */
public class DateFrameCreate {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("DateFrameCreate")
                .setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //创建SQLContext
        SQLContext sqlContext = new SQLContext(sc);

        //从本地加载数据
        Dataset<Row> df = sqlContext
                .read()
                .json("D:\\wordcount\\students.json");
        df.show();


    }
}

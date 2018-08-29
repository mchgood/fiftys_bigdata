package cn.fiftys.spark.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

/**
 * datafrema的一些基本操作
 */
public class DataFrameOperation {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().
                setAppName("DataFrameOperation")
                .setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");
        SQLContext sqlContext = new SQLContext(sc);

        //创建出来的DataFream完全可以理解完一张表数据
        Dataset<Row> df = sqlContext.read().json("D:\\wordcount\\students.json");

        //打印DataFream中所有数据
        df.show();
        //打印Datafream中的元数据
        df.printSchema();
        //打印某列中所有的数据
        df.select("name").show();
        //打印某几列所有的数据,并对列进行计算
        df.select(df.col("name"), df.col("age").plus(1)).show();
        //根据某一列的值进行过滤
        df.filter(df.col("age").gt(18)).show();
        //根据某一列进行分组,然后聚合
        df.groupBy(df.col("age")).count().show();
    }
}

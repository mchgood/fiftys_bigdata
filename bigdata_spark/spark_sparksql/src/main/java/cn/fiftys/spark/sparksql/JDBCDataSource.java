package cn.fiftys.spark.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在数据库中首先创建三张表
 *  student_infos 信息表
 *  student_scores 成绩表
 *  将信息表与成绩表关联过滤出成绩低于80分的学生;
 *  然后将关联后的数据保存到good_student_infos
 *  good_student_infos 信息加上成绩表
 */
public class JDBCDataSource {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("JDBCDataSource");
        SparkContext sc = new SparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        //分别将mysql中的两张表的数据加载为DataFrema
        Map<String, String> options = new HashMap<String, String>();
        options.put("url", "jdbc:mysql://node1:3306/sparktest");
        options.put("dbtable", "student_infos");

        //加载studentInfos表
        Dataset<Row> studentInfos = sqlContext.read().format("jdbc").options(options).load();
        //加载studentScores表
        Dataset<Row> studentScores = sqlContext.read().format("jdbc").options(options).load();

        //将两张表转换为RDD进行join操作
        JavaPairRDD<String, Tuple2<Integer, Integer>> studentRDD = studentInfos.javaRDD().mapToPair(new PairFunction<Row, String, Integer>() {
            public Tuple2<String, Integer> call(Row row) throws Exception {
                return new Tuple2<String, Integer>(row.getString(0),
                        Integer.valueOf(String.valueOf(row.get(1))));
            }
        }).join(studentScores.javaRDD().mapToPair(new PairFunction<Row, String, Integer>() {
            public Tuple2<String, Integer> call(Row row) throws Exception {
                return new Tuple2<String, Integer>(String.valueOf(row.get(0)),
                        Integer.valueOf(String.valueOf(row.get(1))));
            }
        }));

        //将JavaPairRDD转换为JavaRDD<row>
        JavaRDD<Row> studentRowRDD = studentRDD.map(new Function<Tuple2<String, Tuple2<Integer, Integer>>, Row>() {
            public Row call(Tuple2<String, Tuple2<Integer, Integer>> v1) throws Exception {
                return RowFactory.create(v1._1, v1._2._1, v1._2._2);
            }
        });

        //过滤出分数大于80分的数据
        JavaRDD<Row> filterStudentRDD = studentRowRDD.filter(new Function<Row, Boolean>() {
            public Boolean call(Row v1) throws Exception {
                if (v1.getInt(2) >= 80) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        //转换为DataFrame
        List<StructField> structFields = new ArrayList<StructField>();
        structFields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
        structFields.add(DataTypes.createStructField("age", DataTypes.IntegerType, true));
        structFields.add(DataTypes.createStructField("score", DataTypes.IntegerType, true));

        StructType structType = DataTypes.createStructType(structFields);

        Dataset<Row> studentDF = sqlContext.createDataFrame(filterStudentRDD, structType);

        Row[] collect = studentDF.collect();

        for (Row row : collect) {
            System.out.println(row);
        }

        studentDF.javaRDD().foreach(new VoidFunction<Row>() {
            public void call(Row row) throws Exception {
                String sql = "insert into good_student_infos values(" +
                        "'" + String.valueOf(row.getString(0)) + "'," +
                        Integer.valueOf(String.valueOf(row.get(1))) + "," +
                        Integer.valueOf(String.valueOf(row.get(2))) + ")";

                Class.forName("com.mysql.jdbc.mysql");
                Connection conn = null;
                Statement stmt = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://node1:3306/sparktest", "root", "123");
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (stmt != null){
                        stmt.close();
                    }
                    if (conn != null){
                        conn.close();
                    }
                }
            }
        });
        sc.stop();
    }
}

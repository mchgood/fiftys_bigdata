package cn.fiftys.spark.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class JSONDataSource {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("JSONDataSource")
                .setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);


        //针对JSON文件,创建DataFrame
        Dataset<Row> studentScoresDF = sqlContext.read().json("D:\\wordcount\\input\\students.json");

        //针对学生的成绩信息注册临时表,查询分数大于80分的学生的姓名
        studentScoresDF.registerTempTable("student_scores");
        Dataset<Row> goodStudentScoresDF = sqlContext.sql(
                "select name,score from student_scores where score >=80");

        //将DataSet转换为rdd,执行transformation操作
        List<String> goodStudentNames = goodStudentScoresDF.javaRDD().map(new Function<Row, String>() {
            public String call(Row v1) throws Exception {
                return v1.getString(0);
            }
        }).collect();


        //然后针对JavaRDD<String>,创建DataFrame
        //(针对包含json串的javaRDD,创建DataFrame)
        List<String> studentInfoJSONs = new ArrayList<String>();
        studentInfoJSONs.add("{\"name\":\"Leo\", \"age\":18}");
        studentInfoJSONs.add("{\"name\":\"Marry\",\"age\":17}");
        studentInfoJSONs.add("{\"name\":\"Jack\",\"age\":19}");
        JavaRDD<String> studentInfoJSONsRDD = sc.parallelize(studentInfoJSONs);

        //将RDD转化位dataFrame
        Dataset<Row> studentInfosDF = sqlContext.read().json(studentInfoJSONsRDD);

        //针对学生的基本信息DataFrame,注册临时表,然后查询分数大于80分的学生
        studentInfosDF.registerTempTable("student_infos");

        //拼接sql
        String sql = "select name,age from student_infos where name in (";
        for (int i = 0; i < goodStudentNames.size(); i++) {
            sql += "'" + goodStudentNames.get(i) + "'";
            if (i < goodStudentNames.size() - 1) {
                sql += ",";
            }
        }
        sql += ")";

        Dataset<Row> goodStudentInfosDF = sqlContext.sql(sql);

        //将DataFrame转换为JavaRDD，再map为JavaPairRDD，然后进行join
        JavaPairRDD<String, Tuple2<Integer, Integer>> goodStudentsRDD =
                goodStudentScoresDF.javaRDD().mapToPair(new PairFunction<Row, String, Integer>() {

                    public Tuple2<String, Integer> call(Row row) throws Exception {
                        return new Tuple2<String, Integer>(row.getString(0),
                                Integer.valueOf(String.valueOf(row.getLong(1))));
                    }
                }).join(goodStudentInfosDF.javaRDD().mapToPair(new PairFunction<Row, String, Integer>() {
                    public Tuple2<String, Integer> call(Row row) throws Exception {
                        return new Tuple2<String, Integer>(row.getString(0),
                                Integer.valueOf(String.valueOf(row.getLong(1))));
                    }
                }));
        // 然后将封装在RDD中的好学生的全部信息，转换为一个JavaRDD<Row>的格式
        // （将JavaRDD，转换为DataFrame）
        JavaRDD<Row> goodStudentRowsRDD = goodStudentsRDD.map(
                new Function<Tuple2<String, Tuple2<Integer, Integer>>, Row>() {
                    public Row call(Tuple2<String, Tuple2<Integer, Integer>> v1) throws Exception {
                        return RowFactory.create(v1._1, v1._2._1, v1._2._2);
                    }
                });

        //创建一份元数据信息,将javaRDD<Row>转换为DataFrame
        ArrayList<StructField> structFields = new ArrayList<StructField>();

        structFields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
        structFields.add(DataTypes.createStructField("score", DataTypes.IntegerType, true));
        structFields.add(DataTypes.createStructField("age", DataTypes.IntegerType, true));

        StructType structType = DataTypes.createStructType(structFields);
        Dataset<Row> goodStudentsDF = sqlContext.createDataFrame(goodStudentRowsRDD, structType);

        // 将好学生的全部信息保存到一个json文件中去
        // （将DataFrame中的数据保存到外部的json文件中去）
        goodStudentsDF.write().format("json").save("D:\\students");
    }

}

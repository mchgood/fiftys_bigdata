package cn.fiftys.spark.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

/**
 * 以编程方式动态指定元数据，将RDD转换为DataFrame
 */
public class RDD2DataFrameProgrammatically {
    public static void main(String[] args){

        SparkConf conf = new SparkConf().setAppName("RDD2DataFrameProgrammatically").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");
        SQLContext sqlContext = new SQLContext(sc);

        //第一步,创建一个普通的RDD,但是必须将其转换位RRD<row>格式
        JavaRDD<String> lines = sc.textFile("D:\\wordcount\\students.txt");

        //第二步,往row里面塞数据要注意要什么格式的数据
        JavaRDD<Row> studentRDD = lines.map(new Function<String, Row>() {
            public Row call(String line) throws Exception {
                String[] lineSplited = line.split(",");
                return RowFactory.create(
                        Integer.valueOf(lineSplited[0]),
                        lineSplited[1],
                        Integer.valueOf(lineSplited[2]));
            }
        });


        //第三步,动态构建元数据
        List<StructField> structFields = new ArrayList<StructField>();
        structFields.add(DataTypes.createStructField("id",DataTypes.IntegerType,true));
        structFields.add(DataTypes.createStructField("name",DataTypes.StringType,true));
        structFields.add(DataTypes.createStructField("age",DataTypes.IntegerType,true));
        StructType structType = DataTypes.createStructType(structFields);

        //第四步,使用动态构造的元数据，将RDD转换为DataFrame
        Dataset<Row> studentDF = sqlContext.createDataFrame(studentRDD, structType);
        
        //第四部,注册临时表
        studentDF.registerTempTable("students");

        Dataset<Row> teenagerDF = sqlContext.sql("select * from students where age < 20");

        List<Row> rows = teenagerDF.javaRDD().collect();

        for (Row row : rows) {
            System.out.println(row);
        }


    }
}

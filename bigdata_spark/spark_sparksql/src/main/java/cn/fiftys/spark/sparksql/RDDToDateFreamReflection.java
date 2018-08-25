package cn.fiftys.spark.sparksql;

import cn.fiftys.spark.pojo.Student;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import java.util.List;

/**
 * 使用反射的方式将RDD转换为DataFream
 */
public class RDDToDateFreamReflection {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("RDDToDateFreamReflection").setMaster("local[2]");

        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");
        SQLContext sqlContext = new SQLContext(sc);

        JavaRDD<String> lines = sc.textFile("D:\\wordcount\\students.txt");
        JavaRDD<Student> student = lines.map(new Function<String, Student>() {
            public Student call(String line) throws Exception {
                String[] lineSplited = line.split(",");
                Student stu = new Student();
                stu.setId(Integer.valueOf(lineSplited[0].trim()));
                stu.setName(lineSplited[1]);
                stu.setAge(Integer.valueOf(lineSplited[2].trim()));
                return stu;
            }
        });

        /**
         * 使用反射的方式,将RDD转换为DateFrame
         * 要求JavaBean必须实现序列化
         */
        Dataset<Row> studentDF = sqlContext.createDataFrame(student, Student.class);

        //拿到DataFrame之后注册为一个临时表,然后针对其中的数据执行sql语句
        studentDF.registerTempTable("students");

        //查询年龄小于等于18岁的学生
        Dataset<Row> teenager = sqlContext.sql("select * from students where age <18");

        //将查询出结果的DateFrame,再次转化为RDD
        JavaRDD<Row> teenagerRDD = teenager.javaRDD();

        //将RDD中的数据映射
        JavaRDD<Student> teenagerStudentRDD = teenagerRDD.map(new Function<Row, Student>() {
            public Student call(Row row) throws Exception {
                Student stu = new Student();
                stu.setAge(row.getInt(0));
                stu.setId(row.getInt(1));
                stu.setName(row.getString(2));
                return stu;
            }
        });

        //将数据cillect回来,打印出来
        List<Student> studentList = teenagerStudentRDD.collect();
        for (Student student1 : studentList) {
            System.out.println(student1);
        }
    }
}

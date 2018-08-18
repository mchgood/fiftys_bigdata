package cn.fiftys.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * 1,新增一个表
 * 2,新增数据
 * 3,获取数据
 * 4,查询数据
 * 5,删除数据
 */
public class HBaseExample {
    public static void main(String[] args) throws IOException {

        //1,创建Hbase连接驱动
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);

        //2,创建一个表 创建表需要管理员权限
        Admin admin = connection.getAdmin();

        //3,创建表需要表的描述信息
        //HTableDescriptor类中无参构造过时,需要使用有参构造,所以创建表之前徐亚首先指定表的名称
        TableName tableName = TableName.valueOf("user2");
        //判断表是否存在
        if (!admin.tableExists(tableName)) {
            System.out.println("****************开始创建表****************");
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            //方法一:创建列簇
           /* String FamilyName = "base_info";
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(FamilyName);*/

           //添加列簇方法二:
            hTableDescriptor.addFamily(new HColumnDescriptor("base_info") );
            admin.createTable(hTableDescriptor);
            System.out.println("****************创建表成功****************");

        } else {
            System.out.println("****************创建表失败,表存在****************");
            //先进入到表
            admin.disableTable(tableName);
            //在删除表
            admin.deleteTable(tableName);
            System.out.println("****************以删除表****************");
        }


    }
}

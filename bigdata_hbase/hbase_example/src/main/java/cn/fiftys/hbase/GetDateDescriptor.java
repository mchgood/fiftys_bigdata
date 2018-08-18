package cn.fiftys.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class GetDateDescriptor {
    public static void main(String[] args) throws IOException {

        //1,创建Hbase连接驱动
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);

        //2,定义表的数据
        TableName tableName = TableName.valueOf("user");

        //3,获取表
        Table table = connection.getTable(tableName);

        //4,获取表的描述信息
        HTableDescriptor tableDescriptor = table.getTableDescriptor();

        //5,获取表的列簇信息
        HColumnDescriptor[] columnFamilies = tableDescriptor.getColumnFamilies();
        for (HColumnDescriptor columnFamily : columnFamilies) {

            //5.1,获取表的columnFamily的字节数组
            byte[] name = columnFamily.getName();

            //5.2,使用Hbase自带的bytes工具类转化成string
            String value = Bytes.toString(name);

            //输出数据
            System.out.println(value);
        }
    }
}

package cn.fiftys.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * 1,添加数据
 */
public class PutDataTable {
    public static void main(String[] args) throws IOException {

        //1,创建Hbase连接驱动
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);

        //2,定义表的名字
        // TableName tableName = TableName.valueOf("user");
        //3,获取表
        Table table = connection.getTable(TableName.valueOf("user"));
        //添加数据
        byte[] rowkey = Bytes.toBytes("rowkey_10");
        byte[] family = Bytes.toBytes("base_info");
        Put zhangsan = new Put(rowkey);

        //数据的填充
        //添加姓名字段,与值
        byte[] nameField = Bytes.toBytes("username");
        byte[] nameValue = Bytes.toBytes("张三");
        zhangsan.addColumn(family,nameField,nameValue);

        //添加性别,与性别值
        byte[] sexField = Bytes.toBytes("sex");
        byte[] sexValue = Bytes.toBytes("1");
        zhangsan.addColumn(family,sexField,sexValue);

        //添加年龄字段,与对应的值
        byte[] birField = Bytes.toBytes("birthday");
        byte[] birValue = Bytes.toBytes("1997-09-09");
        zhangsan.addColumn(family,birField,birValue);

        //添加年龄字段,与对应的值
        byte[] addressField = Bytes.toBytes("address");
        byte[] addressValue = Bytes.toBytes("北京市");
        zhangsan.addColumn(family,addressField,addressValue);

        //数据提交
        table.put(zhangsan);

        //关闭连接
        table.close();
    }
}

package cn.fiftys.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class GetDataTable {
    public static void main(String[] args) throws IOException {

        //1,创建Hbase连接驱动
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);
        //2,指定表
        TableName tableName = TableName.valueOf("user");
        //3,连接到表
        Table table = connection.getTable(tableName);
        //4,准备数据
        String rowkey = "rowkey_10";
        //5,拼接封装条件
        Get get = new Get(Bytes.toBytes(rowkey));
        //6,查询数据
        Result result = table.get(get);
        //7,打印数据获取所有的单元格
        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
            //打印rowkey,family,qualifier,value
            System.out.println(Bytes.toString(CellUtil.cloneRow(cell))
                    + "==> " + Bytes.toString(CellUtil.cloneFamily(cell))
                    + "{" + Bytes.toString(CellUtil.cloneQualifier(cell))
                    + ":" + Bytes.toString(CellUtil.cloneValue(cell)) + "}");
        }
        //关闭数据
        table.close();

    }
}

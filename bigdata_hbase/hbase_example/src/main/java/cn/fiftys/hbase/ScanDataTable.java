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

public class ScanDataTable {
    public static void main(String[] args) throws IOException {
        //1,创建Hbase连接驱动
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);
        //2,指定表
        TableName tableName = TableName.valueOf("user");
        //3,连接到表
        Table table = connection.getTable(tableName);
        //4,全表扫描
        ResultScanner scanner = table.getScanner(new Scan());
        Result result = null;
        //5,迭代数据
        while ((result = scanner.next()) != null){
            //6,打印数据获取全部单元格
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                // 打印rowkey,family,qualifier,value
                System.out.println(Bytes.toString(CellUtil.cloneRow(cell))
                        + "==> " + Bytes.toString(CellUtil.cloneFamily(cell))
                        + "{" + Bytes.toString(CellUtil.cloneQualifier(cell))
                        + ":" + Bytes.toString(CellUtil.cloneValue(cell)) + "}");
            }
        }
        table.close();

    }
}

package cn.fiftys.hbase.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class HBaseUtils {

    private static Connection connection;

    /**
     * 获取Hbase客户端连接
     *
     * @return
     */
    private static Connection getConnection() {
        if (connection == null) {
            try {
                Configuration config = HBaseConfiguration.create();
                connection = ConnectionFactory.createConnection(config, Executors.newFixedThreadPool(30));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 创建表的方法
     *
     * @param tName  需要创建的表的名称
     * @param fNames 需要指定表的列簇,可指定多个
     * @throws IOException
     */
    public static void createTable(String tName, String... fNames) throws IOException {
        Admin admin = getConnection().getAdmin();
        TableName tableName = TableName.valueOf(tName);
        //判断表是否存在
        if (!admin.tableExists(tableName)) {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String fName : fNames) {
                //添加列簇:
                hTableDescriptor.addFamily(new HColumnDescriptor(fName));
            }
            admin.createTable(hTableDescriptor);
        } else {
            new RuntimeException("创建表异常");
        }
        admin.close();
    }

    /**
     * 添加数据到表中
     *
     * @param tName  指定需要put数据的表名
     * @param rowkey 行键值
     * @param cf     指定put表的列簇
     * @param filed  指定表的字段
     * @param value  指定表的字段所对应的值
     * @throws IOException
     */
    public static void putDate(String tName, String rowkey, String cf, String filed, String value) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tName));
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(filed), Bytes.toBytes(value));
        table.put(put);
        table.close();

    }

    /**
     * 查询方法
     * 封装get方法
     *
     * @param tName  表明
     * @param rowkey 行键值
     * @param cf     列簇
     * @param filed  字段
     * @return
     * @throws IOException
     */
    public static ArrayList<Map<String, String>> get(String tName, String rowkey, String cf, String filed) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tName));
        Get get = new Get(Bytes.toBytes(rowkey));
        if (null != cf) {
            get.addFamily(Bytes.toBytes(cf));
        }
        if (null != filed) {
            get.addColumn(Bytes.toBytes(cf), Bytes.toBytes(filed));
        }

        Result result = table.get(get);
        table.close();
        return getValue(result);
    }

    /**
     * 封装结果集
     * @param result
     * @return
     * @throws IOException
     */
    private static ArrayList<Map<String, String>> getValue(Result result) throws IOException {
        ArrayList<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("Rowkey", Bytes.toString(CellUtil.cloneRow(cell)));
            hashMap.put("FamilyName", Bytes.toString(CellUtil.cloneFamily(cell)));
            hashMap.put("Filed", Bytes.toString(CellUtil.cloneQualifier(cell)));
            hashMap.put("Value", Bytes.toString(CellUtil.cloneValue(cell)));
            maps.add(hashMap);
        }

        return maps;
    }

    /**
     * 封装scan查询方法
     * @param tName 表的名称
     * @param startKey 起始行键值
     * @param stopKey  停止行键值
     * @param filter   过滤器
     * @throws IOException
     */
    public static ArrayList<ArrayList<Map<String, String>>> scan(String tName, String startKey, String stopKey, Filter filter) throws IOException {
        ArrayList<ArrayList<Map<String, String>>> lists = new ArrayList<ArrayList<Map<String, String>>>();
        Table table = getConnection().getTable(TableName.valueOf(tName));
        Scan scan = new Scan();
        if (null != startKey && null != stopKey){
            scan.setStartRow(Bytes.toBytes(startKey));
            scan.setStopRow(Bytes.toBytes(stopKey));
        }
        if (null != filter){
            scan.setFilter(filter);
        }
        Result result;
        ResultScanner scanner = table.getScanner(scan);
        while ((result = scanner.next()) != null){
            ArrayList<Map<String, String>> value = getValue(result);
            lists.add(value);
        }
        table.close();
        return lists;

    }




























}

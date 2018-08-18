package cn.fiftys.hbase;

import cn.fiftys.hbase.utils.HBaseUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * 测试封装的工具类
 */
public class Test {
    public static void main(String[] args) throws IOException {

        //创建表
        HBaseUtils.createTable("user3","cf1");

        //插入数据
        HBaseUtils.putDate("user3","10000","cf1","username","yummy");
        HBaseUtils.putDate("user3","10000","cf1","age","18");
        HBaseUtils.putDate("user3","10000","cf1","sex","1");
        HBaseUtils.putDate("user3","10000","cf1","address","上海");
        //查询数据
        ArrayList<Map<String, String>> maps = HBaseUtils.get("user3", "10000", null, null);
        for (Map<String, String> map : maps) {
            System.out.println(map);
        }

        /*//测试Scan扫描
        ValueFilter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("上"));
        ArrayList<ArrayList<Map<String, String>>> user3 = HBaseUtils.scan("user3", null, null, valueFilter);
        for (ArrayList<Map<String, String>> maps : user3) {
            for (Map<String, String> map : maps) {
                System.out.println(map);
            }
        }*/

    }
}

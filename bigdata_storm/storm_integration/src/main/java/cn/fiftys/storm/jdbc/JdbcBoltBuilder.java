package cn.fiftys.storm.jdbc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import org.apache.storm.jdbc.common.Column;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.mapper.JdbcMapper;
import org.apache.storm.jdbc.mapper.SimpleJdbcMapper;
import org.apache.storm.topology.base.BaseRichBolt;

import java.util.List;
import java.util.Map;

public class JdbcBoltBuilder {

     public static BaseRichBolt getJdbcBolt(){
         Map hikariConfigMap = Maps.newHashMap();

         hikariConfigMap.put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
         hikariConfigMap.put("dataSource.url", "jdbc:mysql://192.168.66.201:3306/storm");
         hikariConfigMap.put("dataSource.user","root");
         hikariConfigMap.put("dataSource.password","123");

         ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);

         List<Column> columnSchema = Lists.newArrayList(
                 new Column("word", java.sql.Types.VARCHAR),
                 new Column("count", java.sql.Types.INTEGER));
         JdbcMapper simpleJdbcMapper = new SimpleJdbcMapper(columnSchema);

         JdbcInsertBolt userPersistanceBolt = new JdbcInsertBolt(connectionProvider, simpleJdbcMapper)
                 .withInsertQuery("INSERT INTO `tb_wordcount` VALUES (NULL, ?, ?, NOW())")
                 .withQueryTimeoutSecs(30);

         return userPersistanceBolt;

     }
}

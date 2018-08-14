package cn.fiftys.storm.redis;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class RedisBolt extends BaseRichBolt {

    private JedisPool jedisPool;


    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        jedisPool = new JedisPool(new JedisPoolConfig(), "node1", 6379);
    }

    public void execute(Tuple input) {
        String word = input.getStringByField("word");
        Integer count = input.getIntegerByField("count");

        //保存到redis中的key
        String key = "wordCount:"+word;
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.set(key,String.valueOf(count));
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}

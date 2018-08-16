package cn.fiftys.storm.redis;

import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.tuple.ITuple;

public class WordCountStroeMapper implements RedisStoreMapper {

    private RedisDataTypeDescription redisDataTypeDescription;


    public WordCountStroeMapper(){
        //定义redis中的数据类型
        this.redisDataTypeDescription = new RedisDataTypeDescription(RedisDataTypeDescription.RedisDataType.STRING);
    }

    public RedisDataTypeDescription getDataTypeDescription() {
        return this.redisDataTypeDescription;
    }

    public String getKeyFromTuple(ITuple iTuple) {
        //生成redis中的key
        String word = iTuple.getStringByField("word");

        return "wordCount:" + word;
    }

    public String getValueFromTuple(ITuple iTuple) {
        //存储到redis中的值
        Integer count = iTuple.getIntegerByField("count");

        return String.valueOf(count);
    }
}

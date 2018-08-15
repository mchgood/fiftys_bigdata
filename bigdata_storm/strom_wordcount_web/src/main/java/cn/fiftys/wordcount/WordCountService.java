package cn.fiftys.wordcount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class WordCountService {

    @Autowired
    private RedisTemplate redisTemplate;


    public Map<String, String> queryData() {
        Set<String> keys = this.redisTemplate.keys("wordCount:*");
        Map<String, String> result = new HashMap<String, String>();
        for (String key : keys) {
            result.put(key.substring(key.indexOf(':') + 1), this.redisTemplate.opsForValue().get(key).toString());
        }
        return result;
    }
}

package cn.fiftys.storm.redis;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * 负责将收到的上游的单词对出现的次数进行统计
 * @author yummy
 */
public class WordCountBolt extends BaseRichBolt {

    private OutputCollector collector;

    private Map<String,Integer> wordMap = new HashMap<String, Integer>();


    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    public void execute(Tuple input) {
        //获取到上游的数据
        String word = input.getStringByField("word");
        //统计每一个单词出现的次数与频率
        Integer count = this.wordMap.get(word);
        if (null == count) {
            count = 0;
        }
        count++;
        wordMap.put(word,count);
        //向下游输出数据,这里有多个字段
        this.collector.emit(new Values(word,count));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        //定义向下流输出的数据
        declarer.declare(new Fields("word","count"));

    }
}

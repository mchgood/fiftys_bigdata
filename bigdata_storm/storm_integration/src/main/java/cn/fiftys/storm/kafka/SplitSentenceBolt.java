package cn.fiftys.storm.kafka;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 将接受的句子按照空格进行切分
 *
 * @author yummy
 */
public class SplitSentenceBolt extends BaseRichBolt {

    private OutputCollector collector;

    /**
     * @param stormConf
     * @param context
     * @param collector
     */
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

        this.collector = collector;

    }

    public void execute(Tuple input) {
        // 通过Tuple的getValueByField获取上游传递的数据，其中"sentence"是定义的字段名称
        String value = input.getStringByField("value");
        if ( null == value){
            return;
        }
        //获取到的数据按照空格进行分割
        String[] split = value.split(" ");
        //遍历每一个单词并向下游输出数据
        for (String s : split) {
            this.collector.emit(new Values(s));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //定义向下游输出的数据
        declarer.declare(new Fields("word"));


    }
}

package cn.fiftys.storm.group;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class PrintBolt extends BaseRichBolt {
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    public void execute(Tuple input) {
        String word = input.getStringByField("word");
        Integer count = input.getIntegerByField("count");

        //打印上流传递的数据
        System.out.println(word + "----->" + count);

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}

package cn.fiftys.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

/**
 * RandomSentenceSpout：随机生成一个英文的字符串，模拟用户的输入；
 *
 * @author yummy
 */
public class RandomSentenceSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;


    private String[] sentences = new String[]{"the cow jumped over the moon", "an apple a day keeps the doctor away",
            "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature"};


    /**
     * 在初始化时只会执行一次
     *
     * @param map       配置信息
     * @param context   应用的上下文
     * @param collector 向下游输出数据的收集器
     */
    public void open(Map map, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

    }

    /**
     * storm框架会不断的调用此方法
     * 处理业务逻辑
     */
    public void nextTuple() {
        //先获取到发送的字符串
        String sentence = this.sentences[new Random().nextInt(sentences.length)];
        //打印输出
        System.out.println("生成的句子为-->" + sentence);
        //向下游输出
        this.collector.emit(new Values(sentence));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * 向下游传递数据的时候要进行的定义
     *
     * @param outputFieldsDeclarer
     */
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        //定义向下游输出的名称
        outputFieldsDeclarer.declare(new Fields("sentence"));

    }
}

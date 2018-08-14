package cn.fiftys.storm.redis;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WordCountRedisTopology {

    public static void main(String[] args) {
        //第一步,定义TopologyBuilder对象,用于构建拓扑
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        //第二步,设置spout
        topologyBuilder.setSpout("RandomSentenceSpout", new RandomSentenceSpout(),2).setNumTasks(2);


        //设置bolt
        topologyBuilder.setBolt("SplitSentenceBolt", new SplitSentenceBolt(),4).localOrShuffleGrouping("RandomSentenceSpout").setNumTasks(4);
        topologyBuilder.setBolt("WordCountBolt", new WordCountBolt(),2).partialKeyGrouping("SplitSentenceBolt",new Fields("count"));
        topologyBuilder.setBolt("RedisBolt", new RedisBolt()).localOrShuffleGrouping("wordCount");

        //第三步,构建Topology对象
        StormTopology topology = topologyBuilder.createTopology();

        //第四步,提交拓扑到集群，这里先提交到本地的模拟环境中进行测试
        LocalCluster localCluster = new LocalCluster();
        Config config = new Config();
        config.setNumWorkers(2);
        localCluster.submitTopology("WordCountTopology",config,topology);
    }
}

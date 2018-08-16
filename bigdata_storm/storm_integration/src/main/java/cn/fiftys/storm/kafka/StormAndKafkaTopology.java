package cn.fiftys.storm.kafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class StormAndKafkaTopology {


    public static void main(String[] args) {
        //构建拓扑
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        KafkaSpoutConfig.Builder<String, String> kafkaSpoutBuilder = KafkaSpoutConfig.builder("node1:9092", "kafka-storm-topic");
        //设置消费组
        kafkaSpoutBuilder.setGroupId("group1");
        //设置spout
        topologyBuilder.setSpout("Kafka_spout", new KafkaSpout(kafkaSpoutBuilder.build()), 3);

        //设置bolt
        topologyBuilder.setBolt("SplitSentenceBolt", new SplitSentenceBolt(),4).localOrShuffleGrouping("Kafka_spout").setNumTasks(4);
        topologyBuilder.setBolt("WordCountBolt", new WordCountBolt(),2).partialKeyGrouping("SplitSentenceBolt", new Fields("word"));
        topologyBuilder.setBolt("PrintBolt", new PrintBolt()).localOrShuffleGrouping("WordCountBolt");

        //得到拓扑
        StormTopology topology = topologyBuilder.createTopology();

        //提交拓扑
        Config config = new Config();
        if (args == null || args.length == 0) {
            // 本地提交
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("StormAndKafkaTopology", config, topology);
        } else {
            // 集群提交
            try {
                // 设置worker数（进程）
                config.setNumWorkers(2);
                StormSubmitter.submitTopology(args[0], config, topology);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

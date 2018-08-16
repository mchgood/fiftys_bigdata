package cn.fiftys.storm.redis;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.redis.bolt.RedisStoreBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class StormAndRedisTopology {

    public static void main(String[] args) {
        //第一步,定义TopologyBuilder对象,用于构建拓扑
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        //第二步,设置spout
        topologyBuilder.setSpout("RandomSentenceSpout", new RandomSentenceSpout());


        //设置bolt
        topologyBuilder.setBolt("SplitSentenceBolt", new SplitSentenceBolt()).localOrShuffleGrouping("RandomSentenceSpout");
        topologyBuilder.setBolt("WordCountBolt", new WordCountBolt()).partialKeyGrouping("SplitSentenceBolt", new Fields("word"));
        //设置redis的bolt
        JedisPoolConfig poolConfig = new JedisPoolConfig.Builder().setHost("node1").setPort(6379).setPassword("123456").build();
        topologyBuilder.setBolt("RedisBolt",new RedisStoreBolt(poolConfig,new WordCountStroeMapper())).localOrShuffleGrouping("WordCountBolt");


        //第三步,构建Topology对象
        StormTopology topology = topologyBuilder.createTopology();
        Config config = new Config();


        //第四步,提交拓扑到集群,本地环境与集群环境
        if (args == null || args.length == 0){
            //本地模式
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("StormAndRedisTopology", config, topology);
        }else {
            //集群模式
            //设置工作进程数
            config.setNumWorkers(2);
            try {
                //提交到集群,并且将参数作为拓扑的名称
                StormSubmitter.submitTopology(args[0],config,topology);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

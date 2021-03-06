package cn.fiftys.storm.redis;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WordCountRedisTopology {

    public static void main(String[] args) {
        //第一步,定义TopologyBuilder对象,用于构建拓扑
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        //第二步,设置spout
        topologyBuilder.setSpout("RandomSentenceSpout", new RandomSentenceSpout(), 2).setNumTasks(2);


        //设置bolt
        topologyBuilder.setBolt("SplitSentenceBolt", new SplitSentenceBolt(), 4).localOrShuffleGrouping("RandomSentenceSpout").setNumTasks(4);
        topologyBuilder.setBolt("WordCountBolt", new WordCountBolt(), 2).partialKeyGrouping("SplitSentenceBolt", new Fields("word"));
        topologyBuilder.setBolt("RedisBolt", new RedisBolt()).localOrShuffleGrouping("WordCountBolt");

        //第三步,构建Topology对象
        StormTopology topology = topologyBuilder.createTopology();
        Config config = new Config();
        //第四步,提交拓扑到集群，这里先提交到本地的模拟环境中进行测试

        if (args == null || args.length == 0){
            //本地模式
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("WordCountRedisTopology", config, topology);
        }else {
            //集群模式
            //设置工作进程数
            config.setNumWorkers(2);
            try {
                //提交到集群,并且将参数作为拓扑的名称
                StormSubmitter.submitTopology(args[0],config,topology);
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            } catch (AuthorizationException e) {
                e.printStackTrace();
            }
        }
    }
}

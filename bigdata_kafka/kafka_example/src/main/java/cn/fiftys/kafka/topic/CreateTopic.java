package cn.fiftys.kafka.topic;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

import java.util.Properties;

/**
 * 使用javaAPI操作kafka集群,创建一个topic
 */
public class CreateTopic {
    public static void main(String[] args) {
        ZkUtils zkUtils = null;

        try {
            //参数：zookeeper的地址，session超时时间，连接超时时间，是否启用zookeeper安全机制
            zkUtils = ZkUtils.apply("node1:2181", 30000, 3000, JaasUtils.isZkSecurityEnabled());

            //命名topic
            String topicName = "my-kafka-topic-test1";

            // 判断topic是否存在
            if (!AdminUtils.topicExists(zkUtils, topicName)) {
                AdminUtils.createTopic(zkUtils, topicName, 1, 1, new Properties(), AdminUtils.createTopic$default$6());
                System.out.println(topicName + "创建成功!!");
            } else {
                System.out.println(topicName + "已存在!!");
            }
        } finally {
            if (null != zkUtils) {
                zkUtils.close();
            }
        }

    }

}

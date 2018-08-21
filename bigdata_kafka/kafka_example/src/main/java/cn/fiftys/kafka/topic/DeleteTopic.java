package cn.fiftys.kafka.topic;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

/**
 * 使用javaAPI删除topic
 */
public class DeleteTopic {
    public static void main(String[] args){
        ZkUtils zkUtils = null;
        try {
                zkUtils = ZkUtils.apply("node1:2181", 30000, 3000, JaasUtils.isZkSecurityEnabled());
                String topicName = "my-kafka-topic-test2";

                //判断topic是否存在
                if (AdminUtils.topicExists(zkUtils, topicName)) {
                    AdminUtils.deleteTopic(zkUtils, topicName);
                    System.out.println(topicName + "topic删除成功");
                } else {
                    System.out.println(topicName + "topic删除失败");

                }
            } finally {
                if (null != zkUtils) {
                    zkUtils.close();
                }

        }


    }
}

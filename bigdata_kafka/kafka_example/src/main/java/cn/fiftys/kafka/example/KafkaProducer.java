package cn.fiftys.kafka.example;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 使用javaAPI创建一个消息生产者
 *
 * @author yummy
 */
public class KafkaProducer {
    public static void main(String[] args) throws InterruptedException {

        Properties conf = new Properties();

        //设置kafka服务列表,多个用逗号隔开
        conf.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092,node2:9092");
        // 设置序列化消息 Key 的类
        conf.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置序列化消息 value 的类
        conf.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //初始化
        org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(conf);
        for (int i = 0; i < 100; i++) {
            ProducerRecord record = new ProducerRecord("my-kafka-topic", "data-" + i);

            //发送消息
            producer.send(record);
            System.out.println("发送消息-->" + i);
            Thread.sleep(100);
        }
        producer.close();
    }
}

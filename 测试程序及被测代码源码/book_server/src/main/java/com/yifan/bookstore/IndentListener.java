package com.yifan.bookstore;

import com.yifan.bookstore.service.IndentManagement;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class IndentListener {
    @Autowired
    private IndentManagement indentManagement;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
@Autowired
private WebSocketServer ws;
    /*@KafkaListener(topics = "indent1", groupId = "group_topic_test")
    public void topic1Listener(ConsumerRecord<String, String> record) {
        indentManagement.createIndent(record.value());
        kafkaTemplate.send("indent2",  "key", record.value());
    }

    @KafkaListener(topics = "indent2", groupId = "group_topic_test")
    public void topic2Listener(ConsumerRecord<String, String> record) {
        String value = record.value();
        System.out.println(value);
    }*/
}

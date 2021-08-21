package com.springboot.kafka.producer.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {
	
	/**
	 * To create topics programmatically.
	 * */
    
    //@Value(value = "${kafka.bootstrapAddress}")
    private static final String bootstrapAddress = "127.0.0.1:2181";
    private static final String TOPIC = "first_topics_json";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic topic1() {
         return new NewTopic(TOPIC, 1, (short) 1);//string_topic_name, int_number_partitions, short_replication_factor
    }
}

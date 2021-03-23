package com.taufik.kafka.producer.main.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.taufik.kafka.producer.main.model.UserDetails;


@Configuration
public class KafkaProducerConfig {
	
	@Bean
	public ProducerFactory<String, UserDetails> producerFactory(){
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 192.168.0.103:9092 - kafka server port
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		DefaultKafkaProducerFactory<String, UserDetails> defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(configs);
		return defaultKafkaProducerFactory;
	}
	
	@Bean
	public KafkaTemplate<String, UserDetails> kafkaTemplate(){
		KafkaTemplate<String, UserDetails> kafkaTemplate = new KafkaTemplate<>(producerFactory());
		return kafkaTemplate;
	}

}

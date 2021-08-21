package com.taufik.kafka.consumer.main;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.taufik.kafka.consumer.main.model.UserDetails;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		DefaultKafkaConsumerFactory<String, String> defaultKafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(configs);
		return defaultKafkaConsumerFactory;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, UserDetails> userDetailsConsumerFactory(){
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		DefaultKafkaConsumerFactory<String, UserDetails> userDetailsKafkaConsumerFactory 
		= new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(UserDetails.class));
		return userDetailsKafkaConsumerFactory;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserDetails> userDetailsConcurrentKafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, UserDetails> userDetailsFactory = new ConcurrentKafkaListenerContainerFactory<>();
		userDetailsFactory.setConsumerFactory(userDetailsConsumerFactory());
		return userDetailsFactory;
	}
	

}

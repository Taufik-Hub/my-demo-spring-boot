package com.springboot.kafka.producer.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.springboot.kafka.producer.model.Product;

@Configuration
public class KafkaProducerConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerConfiguration.class);
	private static final String SERVER_ADDRESS = "127.0.0.1:9092";

	@Bean
	public ProducerFactory<String, Product> producerConfiguration() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_ADDRESS);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<String, Product>(properties);
	}

	@Bean
	public KafkaTemplate<String, Product> kafkaTemplate() {
		return new KafkaTemplate<>(producerConfiguration());
	}

}

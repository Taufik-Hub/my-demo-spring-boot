package com.springboot.kafka.consumer.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.springboot.kafka.consumer.model.Product;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfiguration.class);
	// @Value("${kafka.bootstrap-servers}")
	private static final String SERVER_ADDRESS = "127.0.0.1:9092";
	private static final String GROUP_ID = "first_topics_group";

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(stringConsumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, String> stringConsumerFactory() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_ADDRESS);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<String, String>(properties);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Product> productKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Product> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(productConsumerFactory());

//		factory.setRetryTemplate(retryTemplate());
//		factory.setErrorHandler(((exception, data) -> {
//			/*
//			 * here you can do you custom handling, I am just logging it same as default
//			 * Error handler does If you just want to log. you need not configure the error
//			 * handler here. The default handler does it for you. Generally, you will
//			 * persist the failed records to DB for tracking the failed records.
//			 */
//			
//			try {
//				LOGGER.info("Error in process with Exception {} and the record is {}", exception, data);
//			}catch (Exception e) {
//				LOGGER.error("Error in process with Exception {} and the record is {}", e);
//			}
//		}));

		return factory;
	}

	@Bean
	public ConsumerFactory<String, Product> productConsumerFactory() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_ADDRESS);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//		properties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
//		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//		properties.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
//		properties.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//		properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.mypackage.app.model.kafka.message.KafkaEvent");
//		properties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.mypackage.app");

		return new DefaultKafkaConsumerFactory<String, Product>(properties, new StringDeserializer(),
				new JsonDeserializer<>(Product.class));
	}

	/** Retry and ErrorHanding */

//	private RetryTemplate retryTemplate() {
//		RetryTemplate retryTemplate = new RetryTemplate();
//		/**
//		 * here retry policy is used to set the number of attempts to retry and what
//		 * exceptions you wanted to try and what you don't want to retry.
//		 */
//		retryTemplate.setRetryPolicy(retryPolicy());
//
//		return retryTemplate;
//	}
//
//	private SimpleRetryPolicy retryPolicy() {
//		Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
//		// the boolean value in the map determines whether exception should be retried
//		exceptionMap.put(IllegalArgumentException.class, false);
//		exceptionMap.put(TimeoutException.class, true);
//		exceptionMap.put(ListenerExecutionFailedException.class, true);
//		return new SimpleRetryPolicy(1, exceptionMap, true);
//	}

}

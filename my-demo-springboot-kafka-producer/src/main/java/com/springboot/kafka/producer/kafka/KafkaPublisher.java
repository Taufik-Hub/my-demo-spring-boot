package com.springboot.kafka.producer.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.springboot.kafka.producer.model.Product;

import reactor.core.publisher.Mono;

@Component
public class KafkaPublisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPublisher.class);
	private static final String TOPIC = "first_topics_json";
	private Product sendMessage = null;

	@Autowired
	private KafkaTemplate<String, Product> kafkaTemplate;

	public Mono<Product> publish(Product message) {
		ListenableFuture<SendResult<String, Product>> future = kafkaTemplate.send(TOPIC, message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Product>>() {

			@Override
			public void onSuccess(SendResult<String, Product> result) {
				LOGGER.info("Publish message : {} on topic : {} with offset : {}", result.getProducerRecord().value(),
						result.getProducerRecord().topic(), result.getRecordMetadata().offset());
				sendMessage = result.getProducerRecord().value();
			}

			@Override
			public void onFailure(Throwable ex) {
				LOGGER.error("Unable to publish message : {} on topic : {} due to : {}", message, TOPIC,
						ex.getMessage());
			}
		});
		return Mono.just(message);
	}

}

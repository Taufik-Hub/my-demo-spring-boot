package com.taufik.kafka.consumer.main.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.taufik.kafka.consumer.main.model.UserDetails;

@Service
public class KafkaConsumerService {

	@KafkaListener(topics = "topic1", groupId = "group_id")
	public void messageConsumer(String message) {
		System.out.println("Consumed message is : " + message);
	}

	@KafkaListener(topics = "topic1", groupId = "group_json", containerFactory = "userDetailsConcurrentKafkaListenerContainerFactory")
	public void userDetailsConsumer(UserDetails userDetails) {
		System.out.println("Consumed JSON message as : " + userDetails);
	}

}

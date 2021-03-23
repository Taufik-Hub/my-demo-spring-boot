package com.taufik.kafka.producer.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taufik.kafka.producer.main.model.UserDetails;


@RestController
@RequestMapping(value = "kafka")
public class KafkaProducerController {

	private static final String TOPIC = "topic-1";

	/**
	 * KafkaTemplate<String, String> kafkaTemplate; - topic and message are in
	 * string so no need to serialize and config
	 */
	//	@Autowired
	//	KafkaTemplate<String, String> kafkaTemplate;
	//	
	//	@GetMapping(value="/publish/{messageName}")
	//	public String publishTopic(@PathVariable ("messageName") String message) {
	//		kafkaTemplate.send(TOPIC, message);
	//		return "Topic : " + TOPIC + " publsh sucessfully with message : " + message;
	//	}

	/**
	 * KafkaTemplate<String, UserDetails> kafkaTemplate; - 
	 * need to config for serialization - check KafkaProducerConfig
	 */
	
	@Autowired
	KafkaTemplate<String, UserDetails> kafkaTemplate;

	@PostMapping(value = "/publish")
	public String publishTopic(@RequestBody UserDetails userDetails) {
		System.out.println("Topic : " + TOPIC + " publsh sucessfully with message : " + userDetails);
		kafkaTemplate.send(TOPIC, userDetails);
		return "Topic : " + TOPIC + " publsh sucessfully with message : " + userDetails;
	}

	@GetMapping(value = "/**")
	public String fallbackMethod() {
		return "Server is up and Running on given port";
	}

}

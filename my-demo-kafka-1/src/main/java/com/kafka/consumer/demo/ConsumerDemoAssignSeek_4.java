package com.kafka.consumer.demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemoAssignSeek_4 {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemoAssignSeek_4.class);

	/**
	 * To read particular set of messages defined by assign and seek terms & No groupId and no need to subscribe
	 * Assign and seek are mostly used to replay data or fetch a specific message
	 * No groupId and no need to subscribe - line commented
	 * seek - to read from specific offset
	 * */
	public static void main(String[] args) {
		// ConsumerConfig
		String bootstrapServers = "127.0.0.1:9092";// localhost:9092 - default port
		String topic = "first-topic";
		//String groupId = "my-first-group";
		String auto_offset_reset = "earliest"; //latest, earliest, none

		// 1. create consumer properties
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		//properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, auto_offset_reset);
		

		// 2.create the consumer
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

		// 3. subscribe consumer to our topic(s)
		// kafkaConsumer.subscribe(Arrays.asList(topic, "second-topic"));
		
		// Assign and seek are mostly used to replay data or fetch a specific message 
		// we want to fetch particular message of topic from particular offset 
		
		// assign
		int partition = 0; //read from partition number 0 from topic = "first-topic"
		TopicPartition topicPartitionToReadFrom = new TopicPartition(topic, partition);
		kafkaConsumer.assign(Arrays.asList(topicPartitionToReadFrom));
		long offsetToReadFrom = 15L;//read from offset 15 and above

		// seek
		kafkaConsumer.seek(topicPartitionToReadFrom, offsetToReadFrom);

		int numberOfMessagesToRead = 5;
		boolean keepOnReading = true;
		int numberOfMesagesReadSoFar = 0;
		//poll
		while(keepOnReading) {
			ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
			for(ConsumerRecord<String, String> record : consumerRecords) {
				numberOfMesagesReadSoFar++;
				LOGGER.info("Consumed Record has key : {}, value : {}, Partition : {}, offset : {}, timestamp : {}",
						record.key(), record.value(), record.partition(), record.offset(), record.timestamp());
				if (numberOfMesagesReadSoFar >= numberOfMessagesToRead) {
					keepOnReading = false;//to exit while loop
					break;//to exit for loop
				}
			}
		}

		// 5. close consumer
		kafkaConsumer.close();
		LOGGER.info("Exiting the application");
		
	}
	
	/**
	 * To read particular set of messages defined by following terms & No groupId and no need to subscribe
	 * 
	 * topic = "first-topic"
	 * read from partition = 0 
	 * offset = 15 read from 15 and above 
	 * applied if condition to read only 5 records 
	 *  
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemoAssignSeek_4 - Consumed Record has key : id_6, value : Hello World : 6, Partition : 0, offset : 15, timestamp : 1626444912965
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemoAssignSeek_4 - Consumed Record has key : id_1, value : Hello World : 1, Partition : 0, offset : 16, timestamp : 1626447152478
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemoAssignSeek_4 - Consumed Record has key : id_3, value : Hello World : 3, Partition : 0, offset : 17, timestamp : 1626447153550
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemoAssignSeek_4 - Consumed Record has key : id_6, value : Hello World : 6, Partition : 0, offset : 18, timestamp : 1626447153608
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemoAssignSeek_4 - Consumed Record has key : id_1, value : Hello World : 1, Partition : 0, offset : 19, timestamp : 1626499961449*/
}

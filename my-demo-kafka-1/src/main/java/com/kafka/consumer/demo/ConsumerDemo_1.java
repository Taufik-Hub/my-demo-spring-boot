package com.kafka.consumer.demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemo_1 {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemo_1.class);

	/**
	 * 1. create Properties
	 * 2. Create KafkaConsumer
	 * 3. subscribe KafkaConsumer  - collection of topic(s)
	 * 3. Read KafkaRecord by polling
	 * 
	 * kafkaConsumer.subscribe("myTopic.*"); 
	 * 
	 * TODO :
	 * 	Set this property, if auto commit should happen
	 * 	properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
	 *  
	 *  Auto commit interval, kafka would commit offset at this interval.
	 *  properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "101");
	 *  
	 *  This is how to control number of records being read in each poll
	 *  properties.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "135");
	 *  
	 *  Set this if you want to always read from beginning.
	 *  props.put("auto.offset.reset", "earliest");
	 *  properties.put("heartbeat.interval.ms", "3000");
	 *  properties.put("session.timeout.ms", "6001");
	 *  
	 * */

	public static void main(String[] args) {
		//// ConsumerConfig
		String bootstrapServers = "127.0.0.1:9092";// localhost:9092 - default port
		String topic = "first-topic";
		String groupId = "my-first-group";
		String auto_offset_reset = "earliest"; //latest, earliest, none

		// 1. create consumer properties
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, auto_offset_reset);

		// 2.create the consumer
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

		// 3. subscribe consumer to our topic(s)
		kafkaConsumer.subscribe(Arrays.asList(topic, "second-topic"));

		while(true) {
			ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
			for(ConsumerRecord<String, String> record : consumerRecords) {
				LOGGER.info("Consumed Record has key : {}, value : {}, Partition : {}, offset : {}, timestamp : {}",
						record.key(), record.value(), record.partition(), record.offset(), record.timestamp());
			}
		}

		// 5. close consumer
		//kafkaConsumer.close();
		
	}
	/**
	 * Output :
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : null, value : Hello World...!!!, Partition : 0, offset : 12, timestamp : 1626444886630
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_0, value : Hello World : 0, Partition : 1, offset : 21, timestamp : 1626444912750
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_1, value : Hello World : 1, Partition : 0, offset : 13, timestamp : 1626444912844
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_2, value : Hello World : 2, Partition : 2, offset : 46, timestamp : 1626444912884
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_3, value : Hello World : 3, Partition : 0, offset : 14, timestamp : 1626444912925
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_4, value : Hello World : 4, Partition : 2, offset : 47, timestamp : 1626444912935
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_5, value : Hello World : 5, Partition : 2, offset : 48, timestamp : 1626444912950
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_6, value : Hello World : 6, Partition : 0, offset : 15, timestamp : 1626444912965
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_7, value : Hello World : 7, Partition : 2, offset : 49, timestamp : 1626444913000
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_8, value : Hello World : 8, Partition : 1, offset : 22, timestamp : 1626444913010
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_9, value : Hello World : 9, Partition : 2, offset : 50, timestamp : 1626444913020
	 * 
	 * */
	

}

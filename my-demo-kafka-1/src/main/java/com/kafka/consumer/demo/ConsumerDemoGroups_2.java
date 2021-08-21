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

public class ConsumerDemoGroups_2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemoGroups_2.class);

	/**
	 * By changing group id we can reset application
	 * i.e. it read all the messages for the topic from beginning to end
	 * i.e. offset = 0 till end
	 * 
	 * # multiple consumers with same group id in that case re-balancing will happen
	 * 
	 * in case of 3 consumers re-balancing will happen automatically as below
	 * C1 : assigned partitions: first-topic-0, second-topic-0 | C2 : assigned partitions: first-topic-2 | C3 : assigned partitions: first-topic-1
	 * 
	 * if one of consumer goes down again re-balancing happen between 2 consumers automatically like below
	 * C1 : assigned partitions: first-topic-1, first-topic-0, second-topic-0 | C2 : assigned partitions: first-topic-2
	 * */

	public static void main(String[] args) {
		//// ConsumerConfig
		String bootstrapServers = "127.0.0.1:9092";// localhost:9092 - default port
		String topic = "first-topic";
		String groupId = "my-second-group";
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
	 * Single consumer
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
	
	/** Multiple consumers - 3 
	 * consumer - 1
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_1, value : Hello World : 1, Partition : 0, offset : 16, timestamp : 1626447152478
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_3, value : Hello World : 3, Partition : 0, offset : 17, timestamp : 1626447153550
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_6, value : Hello World : 6, Partition : 0, offset : 18, timestamp : 1626447153608
	 * 
	 * consumer - 2
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_0, value : Hello World : 0, Partition : 1, offset : 23, timestamp : 1626447152280
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_8, value : Hello World : 8, Partition : 1, offset : 24, timestamp : 1626447153620
	 * 
	 * consumer - 3
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_2, value : Hello World : 2, Partition : 2, offset : 51, timestamp : 1626447152907
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_4, value : Hello World : 4, Partition : 2, offset : 52, timestamp : 1626447153553
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_5, value : Hello World : 5, Partition : 2, offset : 53, timestamp : 1626447153569
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_7, value : Hello World : 7, Partition : 2, offset : 54, timestamp : 1626447153610
	 * [main] INFO com.kafka.consumer.demo.ConsumerDemo_1 - Consumed Record has key : id_9, value : Hello World : 9, Partition : 2, offset : 55, timestamp : 1626447153624
	 * */

}

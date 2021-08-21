package com.kafka.producer.demo;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemo_1 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDemo_1.class);
	// IntelliJ Ctrl + P
	/** 
	 *  *** Kafka Producer ***
	 * 
	 * default port :  Zookeeper 2181 and kafka 9092
	 * 127.0.0.1:9092 OR localhost:9092 
	 * 
	 * 1. Start Zookeeper : zookeeper-server-start.bat  config\zookeeper.properties 
	 * 2. Start Kafka Server/cluster/broker : kafka-server-start.bat config\server.properties 
	 * 3. Create topic : kafka-topics --create --zookeeper 127.0.0.1:2181  --topic first_topic --partitions 3  --replication-factor 1 
	 * 4. This class is kafka producer 
	 * 4. Create consumer for topic : kafka-console-consumer --bootstrap-server  127.0.0.1:9092 --topic first-topic --group my-first-group
	 **/
	
	public static void main(String[] args) {
		//ProducerConfig
		String bootstrapServers = "127.0.0.1:9092";//localhost:9092 - default port
		String topic = "first-topic";
		
		//1. create producer properties
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,  StringSerializer.class.getName());
		//first-topic
		
		//2.create the producer
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
		
		//3. create a Producer Record 
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, "Hello World...!!!");
		
		//4.send date - asynchronous
		kafkaProducer.send(producerRecord);
		
		
		//5.flush or close
		//kafkaProducer.flush(); // only flush
		kafkaProducer.close(); // flush and close at producer end 
	}

}

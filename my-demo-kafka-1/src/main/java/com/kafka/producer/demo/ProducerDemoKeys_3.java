package com.kafka.producer.demo;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoKeys_3 {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDemoKeys_3.class);
	/**
	 * *** Keys ***
	 * 
	 * Message associated with same key always goes to same partition for a fixed number of partition (even in different system as well)
	 * 
	 * Note : kafkaProducer.get(); -> block send() to make it synchronous - do not do this in production
	 * 
	 * */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// ProducerConfig
		String bootstrapServers = "127.0.0.1:9092";// localhost:9092 - default port
		String topic = "first-topic";
		String value = "Hello World";
		String key = "id_";

		// 1. create producer properties
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		// first-topic

		// 2.create the producer
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

		for (int i = 0; i < 10; i++) {
			// 3. create a Producer Record
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, key + i, value + " : " + i);

			LOGGER.info("printing key : {}", key + i);
			// 4.send date - asynchronous
			kafkaProducer.send(producerRecord, new Callback() {//Anonymous inner class //we created object of child class of Callback class
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception == null) {
						LOGGER.info("Received new Metadata Topic :  {}, partition: {}, Offsets : {}, Timestamp : {} ",
								metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
						// Received new Metadata Topic : first-topic, partition: 1, Offsets : 3,
						// Timestamp : 1626419967586
					} else {
						LOGGER.error("Error while producing ", exception);
					}

				}
			}).get();//block send() to make it synchronous - don't do this in production - for understanding purpose we used
		}
		kafkaProducer.flush(); // only flush
		kafkaProducer.close(); // flush and close at producer end
		
	}
}

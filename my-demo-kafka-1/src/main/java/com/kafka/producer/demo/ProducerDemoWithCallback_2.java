package com.kafka.producer.demo;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoWithCallback_2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDemoWithCallback_2.class);

	/**
	 * Callback function provide metadata of produced kafka record or message so
	 * which help confirm it is delivered to the Kafka cluster properly
	 */

	public static void main(String[] args) {
		// ProducerConfig
		String bootstrapServers = "127.0.0.1:9092";// localhost:9092 - default port
		String topic = "first-topic";

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
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic,
					"Hello World callback..." + i);

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
			});
		}
		//5. flush or close and flush
		//kafkaProducer.flush(); // only flush
		kafkaProducer.close(); // flush and close at producer end
	}

}

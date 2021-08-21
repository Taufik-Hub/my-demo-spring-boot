package com.kafka.consumer.demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemoWithThreads_3 {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemoWithThreads_3.class);

	
	/**
	 * Remove while(true) infinite loop for consuming u.e better way to shutdown application
	 * CountDownLatch and consumerRunnable thread
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

		// 4. read data by using CountDownLatch and consumerRunnable thread
		
		//latch for dealing with multiple threads
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		//create consumer runnable to consume data and started thread
		Runnable myConsumerRunnable = new ConsumerRunnable(countDownLatch, kafkaConsumer);
		Thread myThread = new Thread(myConsumerRunnable);
		myThread.start();
		
		//add shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			LOGGER.info("Caught shutdown hook");
			((ConsumerRunnable) myConsumerRunnable).shutdown();
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.info("Application has exited");
		}
		));
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			LOGGER.error("Application got interrupted");
		} finally {
			LOGGER.info("Application is closing");
		}

		// 5. close consumer
		//kafkaConsumer.close();
		
	}
	
}

class ConsumerRunnable implements Runnable{
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRunnable.class);
	private CountDownLatch countDownLatch;
	private KafkaConsumer<String, String> kafkaConsumer;
	
	public ConsumerRunnable(CountDownLatch countDownLatch, KafkaConsumer<String, String> kafkaConsumer) {
		this.countDownLatch = countDownLatch;
		this.kafkaConsumer = kafkaConsumer;
	}

	public void run() {
		try {
			//poll for new data
			while (true) {
				ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> record : consumerRecords) {
					LOGGER.info("Consumed Record has key : {}, value : {}, Partition : {}, offset : {}, timestamp : {}",
							record.key(), record.value(), record.partition(), record.offset(), record.timestamp());
				}
			}
		} catch (WakeupException ex) {
			LOGGER.info("Received shutdown signal.");
		} finally {
			kafkaConsumer.close();
			// to tell our main code that we done with consuming
			countDownLatch.countDown();
		}
	}

	public void shutdown() {
		//wakeup() method is special method to interrupt kafkaConsumer.poll
		//It will throw WakeupException
		kafkaConsumer.wakeup();
	}

}

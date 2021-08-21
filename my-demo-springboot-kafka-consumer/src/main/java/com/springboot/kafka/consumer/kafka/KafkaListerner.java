package com.springboot.kafka.consumer.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.springboot.kafka.consumer.model.Product;

@Component
public class KafkaListerner {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListerner.class);

	@KafkaListener(topics = "first_topics_json", groupId = "first_topics_group_json", containerFactory = "productKafkaListenerContainerFactory")
	public void consumerJsonMessage(Product product) {
		LOGGER.info("Inside KafkaConsumer : {}", product);
	}

	@KafkaListener(topics = "first_topics_string", groupId = "first_topics_group_string", containerFactory = "kafkaListenerContainerFactory")
	public void firstStringMessageConsumer(String value) {
		LOGGER.info("firstStringMessageConsumer is consuming : {}", value);
	}

	@KafkaListener(topics = "first_topics_string", groupId = "first_topics_group_string_other", containerFactory = "kafkaListenerContainerFactory")
	public void secondStringMessageConsumer(String value) {
		LOGGER.info("secondStringMessageConsumer is consuming : {}", value);
	}

	@KafkaListener(topics = "first_topics_string", groupId = "first_topics_group_string_other", containerFactory = "kafkaListenerContainerFactory")
	public void thirdStringMessageConsumer(String value) {
		LOGGER.info("thirdStringMessageConsumer is consuming : {}", value);
	}

	/**
	 * #Consumer-group is re-balance and share load Example: consumer-group
	 * 
	 * Producer -> P1 -> topic_1 -> A B C D E F G H 2-consumer-group | SAME_GROUP_ID
	 * | SAME_TOPIC i.e message load balance/split in 2 consumer-groups with same
	 * name and same topic consuming consumer group -> G1 -> topic_1 -> A C E G
	 * consumer group -> G1 -> topic_1 -> B D F H
	 * 
	 * Producer -> P1 -> topic_1 -> A B C D E F G H 2-consumer-group |
	 * DIFFERENT_GROUP_ID | SAME_TOPIC consumer group -> G1 -> topic_1 -> A B C D E
	 * F G H consumer group -> G2 -> topic_1 -> A B C D E F G H
	 */

	/**
	 * @KafkaListener(topics = "topicName", groupId = "foo")
	 * @KafkaListener(topics = "topic1, topic2", groupId = "foo") //If we don't need
	 *                       to set the offset, we can use the partitions property
	 *                       of @TopicPartition annotation to set only the
	 *                       partitions without the offset
	 * @KafkaListener(topicPartitions = @TopicPartition(topic = "topicName",
	 *                                partitions = { "0", "1" })) //Consuming
	 *                                Messages from a Specific Partition
	 * @KafkaListener(topicPartitions = @TopicPartition(topic = "topicName",
	 *                                partitionOffsets =
	 *                                { @PartitionOffset(partition = "0",
	 *                                initialOffset = "0"),
	 * @PartitionOffset(partition = "3", initialOffset = "0")}), containerFactory =
	 *                            "partitionsKafkaListenerContainerFactory")
	 * @KafkaListener(topics = "topicName", containerFactory =
	 *                       "productKafkaListenerContainerFactory") //Adding
	 *                       Message Filter for Listeners -
	 *                       productKafkaListenerContainerFactory public void
	 *                       listenWithHeaders(@Payload String message,
	 * @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition){//Access Payload
	 *                                             and Multiple headers
	 *                                             System.out.println("Received
	 *                                             Message in group foo: " +
	 *                                             message); }
	 */

}

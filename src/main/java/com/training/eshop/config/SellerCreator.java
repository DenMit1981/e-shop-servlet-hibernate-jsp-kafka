package com.training.eshop.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class SellerCreator {

    public static final String KAFKA_BROKERS = "localhost:9092";
    public static final String TOPIC_NAME = "Order";
    public static final String GROUP_ID_CONFIG = "consumerGroup10";
    public static final String OFFSET_RESET_EARLIER = "earliest";
    public static final Integer MAX_POLL_RECORDS = 1;

    public static Consumer<Long, String> createSeller() {
        final Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, MAX_POLL_RECORDS);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_RESET_EARLIER);

        final Consumer<Long, String> seller = new KafkaConsumer<>(props);

        seller.subscribe(Collections.singletonList(TOPIC_NAME));

        return seller;
    }
}

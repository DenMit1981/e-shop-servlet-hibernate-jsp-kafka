package com.training.eshop.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.eshop.config.BuyerCreator;
import com.training.eshop.config.SellerCreator;
import com.training.eshop.dto.OrderDto;
import com.training.eshop.service.MessageService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;

public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LogManager.getLogger(MessageServiceImpl.class.getName());
    public static final String TOPIC_NAME = "Order";
    private static final long TIMEOUT = 10;
    public static final Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;

    @Override
    public void sendMessageToSeller(OrderDto orderDto) throws JsonProcessingException {
        Producer<Long, String> seller = BuyerCreator.createBuyer();

        ObjectMapper objectMapper = new ObjectMapper();
        String orderAsMessage = objectMapper.writeValueAsString(orderDto);

        final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC_NAME,
                "Order: " + orderAsMessage);

        try {
            seller.send(record).get();
            LOGGER.info("Order has been produced {}", orderAsMessage);

        } catch (ExecutionException | InterruptedException ex) {
            LOGGER.error("Error in sending record");

            LOGGER.error(ex);
        }
    }

    @Override
    public void receiveMessageBySeller() {
        Consumer<Long, String> seller = SellerCreator.createSeller();

        int noMessageToFetch = 0;

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords = seller.poll(TIMEOUT);
            if (consumerRecords.count() == 0) {
                noMessageToFetch++;
                if (noMessageToFetch > MAX_NO_MESSAGE_FOUND_COUNT)
                    break;
                else
                    continue;
            }

            consumerRecords.forEach(record -> {
                String value = record.value();

                LOGGER.info("Create order request has been received: " + value);
            });
            seller.commitAsync();
        }

        seller.close();
    }
}

package com.training.eshop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.eshop.model.Order;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class OrderSerializer implements Serializer<Order> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Order order) {
        byte[] retVal = null;

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            retVal = objectMapper.writeValueAsString(order).getBytes();
        } catch (Exception exception) {
            System.out.println("Error in serializing object" + order);
        }

        return retVal;
    }

    @Override
    public void close() {

    }
}

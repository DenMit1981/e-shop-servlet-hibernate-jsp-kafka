package com.training.eshop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.eshop.model.Order;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class OrderDeserializer implements Deserializer<Order> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Order deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        Order order = null;

        try {
            order = mapper.readValue(data, Order.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes " + exception);
        }
        return order;
    }

    @Override
    public void close() {

    }
}

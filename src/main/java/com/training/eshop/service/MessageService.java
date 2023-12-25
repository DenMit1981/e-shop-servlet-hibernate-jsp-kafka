package com.training.eshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.training.eshop.dto.OrderDto;

public interface MessageService {

    void sendMessageToSeller(OrderDto orderDto) throws JsonProcessingException;

    void receiveMessageBySeller();
}

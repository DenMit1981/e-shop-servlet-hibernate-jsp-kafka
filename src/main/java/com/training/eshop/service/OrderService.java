package com.training.eshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.training.eshop.model.Good;
import com.training.eshop.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    Order save(Order order, String login) throws JsonProcessingException;

    void addGoodToOrder(String goodOption, String login);

    void deleteGoodFromOrder(String goodOption, String login);

    List<Good> getCartGoods();

    String getChosenGoods(Order order);

    String printOrder(Order order);

    BigDecimal getTotalPrice(Order order);

    void clearCartGoods(List<Good> chosenGoods);

    boolean isProductPresent(String goodOption);
}


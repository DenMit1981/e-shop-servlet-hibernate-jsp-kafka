package com.training.eshop.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.training.eshop.converter.OrderConverter;
import com.training.eshop.converter.impl.OrderConverterImpl;
import com.training.eshop.dao.OrderDAO;
import com.training.eshop.dao.impl.OrderDAOImpl;
import com.training.eshop.dto.OrderDto;
import com.training.eshop.model.Good;
import com.training.eshop.model.Order;
import com.training.eshop.model.User;
import com.training.eshop.service.GoodService;
import com.training.eshop.service.MessageService;
import com.training.eshop.service.OrderService;
import com.training.eshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class.getName());
    private static final String ORDER_NOT_MADE = "Make your order\n";
    private static final String CHOSEN_GOODS = "You have already chosen:\n\n";
    private static final String EMPTY_VALUE = "";

    private final OrderDAO orderDAO;
    private final GoodService goodService;
    private final UserService userService;
    private final MessageService messageService;
    private final OrderConverter orderConverter;
    private final List<Good> goods = new ArrayList<>();

    public OrderServiceImpl() {
        orderDAO = new OrderDAOImpl();
        orderConverter = new OrderConverterImpl();
        goodService = new GoodServiceImpl();
        userService = new UserServiceImpl();
        messageService = new MessageServiceImpl();
    }

    @Override
    public Order save(Order order, String login) throws JsonProcessingException {
        setOrderParameters(order, login);

        orderDAO.save(order);

        OrderDto orderDto = orderConverter.convertToOrderDto(order);

        messageService.sendMessageToSeller(orderDto);
        messageService.receiveMessageBySeller();

        LOGGER.info("New order: {}", order);

        return order;
    }

    @Override
    public void addGoodToOrder(String goodOption, String login) {
        Good good = goodService.getGoodFromOption(goodOption);

        Good orderGood = new Good();

        setOrderGoodParameters(good, orderGood, login);

        goods.add(orderGood);

        LOGGER.info("Your goods: {}", goods);
    }

    @Override
    public void deleteGoodFromOrder(String goodOption, String login) {
        Good good = goodService.getGoodFromOption(goodOption);

        Good orderGood = new Good();

        setOrderGoodParameters(good, orderGood, login);

        goods.remove(orderGood);

        LOGGER.info("Your goods after removing {} : {}", good.getTitle(), goods);
    }

    @Override
    public List<Good> getCartGoods() {
        return goods;
    }

    @Override
    public String getChosenGoods(Order order) {
        if (order.getGoods().isEmpty()) {
            return ORDER_NOT_MADE;
        }

        StringBuilder sb = new StringBuilder(CHOSEN_GOODS);

        int count = 1;

        for (Good good : order.getGoods()) {
            sb.append(count)
                    .append(") ")
                    .append(good.getTitle())
                    .append(" ")
                    .append(good.getPrice())
                    .append(" $\n");

            count++;
        }

        return sb.toString();
    }


    @Override
    public String printOrder(Order order) {
        if (order.getGoods().isEmpty()) {
            return ORDER_NOT_MADE;
        }

        return "your order:\n\n" + getChosenGoods(order).replace(CHOSEN_GOODS, EMPTY_VALUE) +
                "\nTotal: $ " + getTotalPrice(order);
    }

    @Override
    public BigDecimal getTotalPrice(Order order) {
        BigDecimal count = BigDecimal.valueOf(0);

        for (Good good : order.getGoods()) {
            count = count.add(good.getPrice());
        }

        return count;
    }

    @Override
    public void clearCartGoods(List<Good> chosenGoods) {
        chosenGoods.clear();
    }

    @Override
    public boolean isProductPresent(String goodOption) {
        Good orderGood = goodService.getGoodFromOption(goodOption);
        String title = orderGood.getTitle();
        String price = String.valueOf(orderGood.getPrice());

        return goods.stream().anyMatch(good -> title.equals(good.getTitle())
                && price.equals(String.valueOf(good.getPrice())));
    }

    private void setOrderParameters(Order order, String login) {
        User user = userService.getByLogin(login).get();
        BigDecimal totalPrice = getTotalPrice(order);

        order.setUser(user);
        order.setGoods(goods);
        order.setTotalPrice(totalPrice);
    }

    private void setOrderGoodParameters(Good good, Good orderGood, String login) {
        User user = userService.getByLogin(login).get();

        orderGood.setId(good.getId());
        orderGood.setTitle(good.getTitle());
        orderGood.setPrice(good.getPrice());
        orderGood.setUser(user);
    }
}

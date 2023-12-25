package com.training.eshop.converter.impl;

import com.training.eshop.converter.GoodConverter;
import com.training.eshop.converter.OrderConverter;
import com.training.eshop.dto.GoodDto;
import com.training.eshop.dto.OrderDto;
import com.training.eshop.model.Good;
import com.training.eshop.model.Order;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderConverterImpl implements OrderConverter {

    private final GoodConverter goodConverter;

    public OrderConverterImpl() {
        goodConverter = new GoodConverterImpl();
    }

    @Override
    public OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();

        List<Good> goods = order.getGoods();

        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setUser(order.getUser().getLogin());
        orderDto.setGoods(convertToGoodDtoList(goods));

        return orderDto;
    }

    @Override
    public List<GoodDto> convertToGoodDtoList(List<Good> goods) {
        return goods
                .stream()
                .map(goodConverter::convertToGoodDto)
                .collect(Collectors.toList());
    }
}

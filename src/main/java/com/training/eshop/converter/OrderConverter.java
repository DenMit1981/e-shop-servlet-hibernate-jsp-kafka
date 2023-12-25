package com.training.eshop.converter;

import com.training.eshop.dto.GoodDto;
import com.training.eshop.dto.OrderDto;
import com.training.eshop.model.Good;
import com.training.eshop.model.Order;

import java.util.List;

public interface OrderConverter {

    OrderDto convertToOrderDto(Order order);

    List<GoodDto> convertToGoodDtoList(List<Good> goods);
}

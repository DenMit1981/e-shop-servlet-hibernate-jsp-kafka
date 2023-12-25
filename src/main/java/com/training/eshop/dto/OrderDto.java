package com.training.eshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    private BigDecimal totalPrice;

    private String user;

    private List<GoodDto> goods = new ArrayList<>();
}

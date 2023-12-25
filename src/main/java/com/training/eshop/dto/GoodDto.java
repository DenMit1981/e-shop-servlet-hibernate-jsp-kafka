package com.training.eshop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class GoodDto {

    private String title;

    private BigDecimal price;
}

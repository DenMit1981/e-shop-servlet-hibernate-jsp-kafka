package com.training.eshop.converter.impl;

import com.training.eshop.converter.GoodConverter;
import com.training.eshop.dto.GoodDto;
import com.training.eshop.model.Good;

public class GoodConverterImpl implements GoodConverter {

    @Override
    public GoodDto convertToGoodDto(Good good) {
        GoodDto goodDto = new GoodDto();

        goodDto.setTitle(good.getTitle());
        goodDto.setPrice(good.getPrice());

        return goodDto;
    }
}

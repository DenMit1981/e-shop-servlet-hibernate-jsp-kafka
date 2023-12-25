package com.training.eshop.service;

import com.training.eshop.dto.GoodDto;
import com.training.eshop.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodService {

    List<GoodDto> getAll();

    Optional<Good> getByTitleAndPrice(String title, String price);

    String getStringOfOptionsForDroppingMenuFromGoodList(List<GoodDto> goods);

    String getStringOfNameAndPriceFromOptionMenu(String menu);

    Good getGoodFromOption(String option);
}

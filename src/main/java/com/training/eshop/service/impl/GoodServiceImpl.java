package com.training.eshop.service.impl;

import com.training.eshop.converter.GoodConverter;
import com.training.eshop.converter.impl.GoodConverterImpl;
import com.training.eshop.dao.GoodDAO;
import com.training.eshop.dao.impl.GoodDAOImpl;
import com.training.eshop.dto.GoodDto;
import com.training.eshop.model.Good;
import com.training.eshop.service.GoodService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GoodServiceImpl implements GoodService {

    private static final String REGEX_LETTERS_FIGURES_POINT = "[^A-Za-z0-9.]";
    private static final String REGEX_ONLY_LETTERS = "[^A-Za-z]";
    private static final String REGEX_ONLY_FIGURES = "[A-Za-z]";
    private static final String EMPTY_VALUE = "";

    private final GoodDAO goodDAO;
    private final GoodConverter goodConverter;

    public GoodServiceImpl() {
        goodDAO = new GoodDAOImpl();
        goodConverter = new GoodConverterImpl();
    }

    @Override
    public List<GoodDto> getAll() {
        return goodDAO.getAll()
                .stream()
                .map(goodConverter::convertToGoodDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Good> getByTitleAndPrice(String title, String price) {
        return goodDAO.getByTitleAndPrice(title, price);
    }

    @Override
    public String getStringOfOptionsForDroppingMenuFromGoodList(List<GoodDto> goods) {
        return goods.stream()
                .map(good -> "<option>" + good.getTitle() + " (" + good.getPrice() + ") </option>\n")
                .collect(Collectors.joining());
    }

    @Override
    public String getStringOfNameAndPriceFromOptionMenu(String menu) {
        return menu.replaceAll(REGEX_LETTERS_FIGURES_POINT, "");
    }

    @Override
    public Good getGoodFromOption(String option) {
        String name = option.replaceAll(REGEX_ONLY_LETTERS, EMPTY_VALUE);
        String price = option.replaceAll(REGEX_ONLY_FIGURES, EMPTY_VALUE);

        return getByTitleAndPrice(name, price).get();
    }
}

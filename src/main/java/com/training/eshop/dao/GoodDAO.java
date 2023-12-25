package com.training.eshop.dao;

import com.training.eshop.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodDAO {

    List<Good> getAll();

    Optional<Good> getByTitleAndPrice(String title, String price);
}

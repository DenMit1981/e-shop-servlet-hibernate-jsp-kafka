package com.training.eshop.dao;

import com.training.eshop.model.User;

import java.util.Optional;

public interface UserDAO {

    void save(User user);

    Optional<User> getByLogin(String login);
}

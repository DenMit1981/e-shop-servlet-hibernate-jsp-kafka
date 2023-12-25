package com.training.eshop.service;

import com.training.eshop.dto.UserDto;
import com.training.eshop.model.User;

import java.util.Optional;

public interface UserService {

    User save(UserDto userDto);

    Optional<User> getByLogin(String login);

    String registerErrors(UserDto userDto);

    String loginErrors(UserDto userDto);
}


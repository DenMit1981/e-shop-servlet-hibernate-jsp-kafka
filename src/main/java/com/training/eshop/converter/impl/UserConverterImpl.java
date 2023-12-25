package com.training.eshop.converter.impl;

import com.training.eshop.converter.UserConverter;
import com.training.eshop.dto.UserDto;
import com.training.eshop.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserConverterImpl implements UserConverter {

    @Override
    public User fromUserDto(UserDto userDto) {
        User user = new User();

        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());

        return user;
    }
}

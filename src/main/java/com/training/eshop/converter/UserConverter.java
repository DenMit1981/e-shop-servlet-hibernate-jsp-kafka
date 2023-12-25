package com.training.eshop.converter;

import com.training.eshop.dto.UserDto;
import com.training.eshop.model.User;

public interface UserConverter {

    User fromUserDto(UserDto userDto);
}

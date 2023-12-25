package com.training.eshop.service.impl;

import com.training.eshop.converter.UserConverter;
import com.training.eshop.converter.impl.UserConverterImpl;
import com.training.eshop.dao.UserDAO;
import com.training.eshop.dao.impl.UserDAOImpl;
import com.training.eshop.dto.UserDto;
import com.training.eshop.model.User;
import com.training.eshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());
    private static final String EMPTY_VALUE = "";
    private static final String FIELD_IS_EMPTY = "Login or password shouldn't be empty";
    private static final String INVALID_FIELD = "Login or password shouldn't be less than 4 symbols";
    private static final String USER_IS_PRESENT = "User with login {} is already present";
    private static final String USER_NOT_FOUND = "You are unregistered user. Please, register right now";
    private static final String USER_HAS_ANOTHER_PASSWORD = "User with login {} has another password. " +
            "Go to register or enter valid credentials";

    private final UserDAO userDAO;
    private final UserConverter userConverter;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
        userConverter = new UserConverterImpl();
    }

    @Override
    public User save(UserDto userDto) {
        User user = userConverter.fromUserDto(userDto);

        userDAO.save(user);

        LOGGER.info("New user : {}", user);

        return user;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return userDAO.getByLogin(login);
    }

    @Override
    public String registerErrors(UserDto userDto) {
        String login = userDto.getLogin();
        String password = userDto.getPassword();

        if (getByLogin(login).isPresent()) {
            LOGGER.error(USER_IS_PRESENT, login);

            return String.format(USER_IS_PRESENT.replace("{}", "%s"), login);
        }

        return getValidErrors(login, password);
    }

    @Override
    public String loginErrors(UserDto userDto) {
        String login = userDto.getLogin();
        String password = userDto.getPassword();

        if (!getByLogin(login).isPresent()) {
            LOGGER.error(USER_NOT_FOUND);

            return USER_NOT_FOUND;
        }

        if (getByLogin(login).isPresent() && !password.equals(getByLogin(login).get().getPassword())) {
            LOGGER.error(USER_HAS_ANOTHER_PASSWORD, login);

            return String.format(USER_HAS_ANOTHER_PASSWORD.replace("{}", "%s"), login);
        }

        return getValidErrors(login, password);
    }

    private String getValidErrors(String login, String password) {
        if (login.length() < 4 || password.length() < 4) {
            if (login.isEmpty() || password.isEmpty()) {
                LOGGER.error(FIELD_IS_EMPTY);

                return FIELD_IS_EMPTY;
            }

            LOGGER.error(INVALID_FIELD);

            return INVALID_FIELD;
        }

        return EMPTY_VALUE;
    }
}

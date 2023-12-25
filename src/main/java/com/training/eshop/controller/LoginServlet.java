package com.training.eshop.controller;

import com.training.eshop.dto.UserDto;
import com.training.eshop.model.User;
import com.training.eshop.service.UserService;
import com.training.eshop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/e-shop")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class.getName());
    private static final String LOGIN_PAGE = "WEB-INF/view/login.jsp";
    private static final String GOODS_PAGE = "WEB-INF/view/goods.jsp";
    private static final String REGISTER_PAGE = "WEB-INF/view/register.jsp";
    private static final String REGISTER_ERRORS = "registerErrors";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String LOGIN_ERRORS = "loginErrors";
    private static final String EMPTY_VALUE = "";

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_ERRORS, EMPTY_VALUE);

        dispatcherForward(request, response, LOGIN_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setPassword(password);

        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_ERRORS, EMPTY_VALUE);
        session.setAttribute("chosenGoods", "Make your order\n");

        if (request.getParameter("submit").equals("Enter")) {
            String errors = userService.loginErrors(userDto);

            if (!errors.isEmpty()) {
                session.setAttribute(LOGIN_ERRORS, errors);

                dispatcherForward(request, response, LOGIN_PAGE);
            } else {
                Optional<User> user = userService.getByLogin(login);

                LOGGER.info(user.get());

                session.setAttribute(LOGIN, user.get().getLogin());
                session.setAttribute(LOGIN_ERRORS, EMPTY_VALUE);

                dispatcherForward(request, response, GOODS_PAGE);
            }

        } else {
            session.setAttribute(REGISTER_ERRORS, EMPTY_VALUE);

            dispatcherForward(request, response, REGISTER_PAGE);
        }
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws
            ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }
}

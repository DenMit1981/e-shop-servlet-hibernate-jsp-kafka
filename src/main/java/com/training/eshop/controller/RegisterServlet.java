package com.training.eshop.controller;

import com.training.eshop.dto.UserDto;
import com.training.eshop.model.User;
import com.training.eshop.service.UserService;
import com.training.eshop.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "WEB-INF/view/login.jsp";
    private static final String REGISTER_PAGE = "WEB-INF/view/register.jsp";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String REGISTER_ERRORS = "registerErrors";
    private static final String EMPTY_VALUE = "";

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, REGISTER_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setPassword(password);

        HttpSession session = request.getSession();

        String errors = userService.registerErrors(userDto);

        if (!errors.isEmpty()) {
            session.setAttribute(REGISTER_ERRORS, errors);

            dispatcherForward(request, response, REGISTER_PAGE);
        } else {
            session.setAttribute(REGISTER_ERRORS, EMPTY_VALUE);

            eventsWithCheckbox(request, response, session, userDto);
        }
    }

    private void eventsWithCheckbox(HttpServletRequest request, HttpServletResponse response, HttpSession session, UserDto userDto) throws IOException, ServletException {
        if (request.getParameter("isUserCheck") != null) {
            User user = userService.save(userDto);

            session.setAttribute(LOGIN, user.getLogin());

            dispatcherForward(request, response, LOGIN_PAGE);
        } else {
            dispatcherForward(request, response, ERROR_PAGE);
        }
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }
}
package com.training.eshop.controller;

import com.training.eshop.model.Good;
import com.training.eshop.model.Order;
import com.training.eshop.service.GoodService;
import com.training.eshop.service.OrderService;
import com.training.eshop.service.impl.GoodServiceImpl;
import com.training.eshop.service.impl.OrderServiceImpl;
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
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/goods")
public class GoodServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(GoodServlet.class.getName());
    private static final String LOGIN_PAGE = "WEB-INF/view/login.jsp";
    private static final String GOODS_PAGE = "WEB-INF/view/goods.jsp";
    private static final String ORDER_PAGE = "WEB-INF/view/order.jsp";
    private static final String CHOSEN_GOODS = "chosenGoods";
    private static final String PRODUCT_NOT_FOUND = "Product not found in the cart";
    private static final String ORDER_NOT_PLACED = "Order not placed yet\n";

    private GoodService goodService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        goodService = new GoodServiceImpl();
        orderService = new OrderServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, GOODS_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Order order = new Order();

        fillCartByGoods(session, order);

        String command = request.getParameter("submit");

        clickingActions(command, request, response, session, order);
    }

    private void clickingActions(String command, HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session, Order order) throws IOException, ServletException {
        BigDecimal totalPrice = orderService.getTotalPrice(order);
        session.setAttribute("totalPrice", totalPrice);

        String option = goodService.getStringOfNameAndPriceFromOptionMenu(request.getParameter("goodName"));
        String login = (String) session.getAttribute("login");

        switch (command) {
            case "Add Good":
                orderService.addGoodToOrder(option, login);

                session.setAttribute(CHOSEN_GOODS, orderService.getChosenGoods(order));

                dispatcherForward(request, response, GOODS_PAGE);

                break;
            case "Remove Good":
                if (!orderService.isProductPresent(option)) {
                    session.setAttribute(CHOSEN_GOODS, PRODUCT_NOT_FOUND);

                    LOGGER.error(PRODUCT_NOT_FOUND);

                    dispatcherForward(request, response, GOODS_PAGE);

                } else {
                    orderService.deleteGoodFromOrder(option, login);

                    session.setAttribute(CHOSEN_GOODS, orderService.getChosenGoods(order));

                    dispatcherForward(request, response, GOODS_PAGE);
                }
                break;
            case "Submit":
                if (order.getGoods().isEmpty()) {
                    session.setAttribute(CHOSEN_GOODS, ORDER_NOT_PLACED);

                    LOGGER.error(ORDER_NOT_PLACED);

                    dispatcherForward(request, response, GOODS_PAGE);
                } else {
                    orderService.save(order, login);

                    session.setAttribute("order", order);

                    dispatcherForward(request, response, ORDER_PAGE);
                }
                break;
            case "Log out":
                dispatcherForward(request, response, LOGIN_PAGE);

                break;
        }
    }

    private void fillCartByGoods(HttpSession session, Order order) {
        List<Good> orderedGoods = orderService.getCartGoods();

        if (session.getAttribute(CHOSEN_GOODS).equals("Make your order\n")) {
            orderService.clearCartGoods(orderedGoods);
        }

        order.setGoods(orderedGoods);
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }
}

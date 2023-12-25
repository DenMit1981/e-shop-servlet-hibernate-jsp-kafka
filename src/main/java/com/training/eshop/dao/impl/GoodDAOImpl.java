package com.training.eshop.dao.impl;

import com.training.eshop.dao.GoodDAO;
import com.training.eshop.model.Good;
import com.training.eshop.utils.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoodDAOImpl implements GoodDAO {

    private static final Logger LOGGER = LogManager.getLogger(GoodDAOImpl.class.getName());
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final String QUERY_SELECT_FROM_GOOD = "from Good";

    @Override
    public List<Good> getAll() {
        List<Good> goods = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            goods = session.createQuery(QUERY_SELECT_FROM_GOOD).list();
        } catch (HibernateException e) {
            LOGGER.error("Request to get all goods has failed. Error message: {}", e.getMessage());
        }

        return goods;
    }

    @Override
    public Optional<Good> getByTitleAndPrice(String title, String price) {
        return Optional.of(getAll().stream()
                        .filter(good -> title.equals(good.getTitle())
                                && price.equals(String.valueOf(good.getPrice())))
                        .findAny())
                .orElse(Optional.empty());
    }
}

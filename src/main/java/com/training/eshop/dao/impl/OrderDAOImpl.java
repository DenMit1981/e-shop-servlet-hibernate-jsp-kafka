package com.training.eshop.dao.impl;

import com.training.eshop.dao.OrderDAO;
import com.training.eshop.model.Order;
import com.training.eshop.utils.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class.getName());
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Order order) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(order);

            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("Request to save new order has failed. Error message: {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}

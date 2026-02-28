package ru.aston.hometask.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.aston.hometask.model.User;

public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackE) {
                    logger.error("Rollback failed", rollbackE);
                }
            }

            logger.error("Error while creating user", e);
            throw new RuntimeException("Error during user creation", e);
        }
    }

    @Override
    public User getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(User.class, id);
        } catch (Exception e) {
            logger.error("Error while fetching user", e);
            throw new RuntimeException("Error fetching user", e);
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackE) {
                    logger.error("Rollback failed", rollbackE);
                }
            }

            logger.error("Error during user update", e);
            throw new RuntimeException("Error while updating the user", e);
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User managed = session.find(User.class, user.getId());
            if (managed != null) {
                session.remove(managed);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackE) {
                    logger.error("Rollback failed", rollbackE);
                }
            }

            logger.error("Error during user deletion", e);
            throw new RuntimeException("Error during user deletion", e);
        }
    }
}

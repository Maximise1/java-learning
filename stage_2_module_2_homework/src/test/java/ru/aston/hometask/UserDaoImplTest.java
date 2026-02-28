package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.aston.hometask.dao.UserDao;
import ru.aston.hometask.dao.UserDaoImpl;
import ru.aston.hometask.model.User;

public class UserDaoImplTest {

    private static SessionFactory sessionFactory;
    private UserDao userDao;

    @BeforeAll
    static void setUp() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    @AfterAll
    static void tearDown() {
        sessionFactory.close();
    }

    @BeforeEach
    void init() {
        userDao = new UserDaoImpl(sessionFactory);
    }

    @Test
    void when_createCalled_then_shouldSaveUser() {
        User user = new User(
                "test@mail.com",
                "Имя",
                100
        );

        userDao.create(user);

        assertNotNull(user.getId());
        assertEquals("test@mail.com", userDao.getById(user.getId()).getEmail());
    }

    @Test
    void when_updateCalled_then_shouldModifyExistingUser() {
        User user = new User("old@mail.com",
                "Имя",
                100
        );

        userDao.create(user);

        user.setEmail("new@mail.com");
        userDao.update(user);

        User updated = userDao.getById(user.getId());

        assertEquals("new@mail.com", updated.getEmail());
    }

    @Test
    void delete_shouldRemoveUser() {
        User user = new User("old@mail.com",
                "Имя",
                100);

        userDao.create(user);
        Long id = user.getId();

        userDao.delete(user);

        User deleted = userDao.getById(id);

        assertNull(deleted);
    }
}

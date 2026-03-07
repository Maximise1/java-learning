package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.aston.hometask.dao.UserDao;
import ru.aston.hometask.dao.UserDaoImpl;
import ru.aston.hometask.dao.UserNotFoundException;
import ru.aston.hometask.model.User;

@Testcontainers
public class UserDaoImplTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");
    private static SessionFactory sessionFactory;
    private UserDao userDao;

    @BeforeAll
    static void setUp() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop")
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
                100,
                null,
                null
        );

        userDao.create(user);

        assertNotNull(user.getId());
        assertEquals("test@mail.com", userDao.getById(user.getId()).getEmail());
    }

    @Test
    void when_updateCalled_then_shouldModifyExistingUser() {
        User user = new User(
                "old@mail.com",
                "Имя",
                100,
                null,
                null
        );

        userDao.create(user);

        user.setEmail("new@mail.com");
        userDao.update(user);

        User updated = userDao.getById(user.getId());

        assertEquals("new@mail.com", updated.getEmail());
    }

    @Test
    void when_deleteCalled_then_shouldRemoveUser() {
        User user = new User(
                "old@mail.com",
                "Имя",
                100,
                null,
                null
        );

        userDao.create(user);
        Long id = user.getId();

        userDao.deleteById(id);

        assertThrows(
                UserNotFoundException.class,
                () -> {
                    userDao.getById(id);
                }
        );
    }
}

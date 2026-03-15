package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import ru.aston.hometask.config.PersistenceConfig;
import ru.aston.hometask.repository.UserRepository;
import ru.aston.hometask.repository.model.User;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {PersistenceConfig.class},
        initializers = UserRepositoryTest.ApplicationContextDatabaseContainerInitializer.class
)
@Transactional
public class UserRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");
    @Autowired
    private UserRepository userRepository;

    static class ApplicationContextDatabaseContainerInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext ctx) {
            postgres.start();

            Map<String, Object> props = new HashMap<>();
            props.put("datasource.url", postgres.getJdbcUrl());
            props.put("datasource.username", postgres.getUsername());
            props.put("datasource.password", postgres.getPassword());
            props.put("hibernate.ddl.auto", "create-drop");

            MapPropertySource propertySource =
                    new MapPropertySource("testcontainers", props);

            ctx.getEnvironment()
                    .getPropertySources()
                    .addFirst(propertySource);
        }
    }

    @Test
    void when_createCalled_then_shouldSaveUser() {
        User user = new User(
                "test@mail.com",
                "Имя",
                100
        );

        userRepository.save(user);

        assertNotNull(user.getId());

        Optional<User> saved = userRepository.findByEmail("test@mail.com");

        assertTrue(saved.isPresent());
        assertEquals("test@mail.com", saved.get().getEmail());
    }

    @Test
    void when_updateCalled_then_shouldModifyExistingUser() {
        User user = new User(
                "mail@mail.com",
                "Имя",
                100
        );

        userRepository.save(user);

        user.setName("New name");
        userRepository.save(user);

        Optional<User> updated = userRepository.findByEmail("mail@mail.com");

        assertTrue(updated.isPresent());
        assertEquals("New name", updated.get().getName());
    }

    @Test
    void when_deleteCalled_then_shouldRemoveUser() {
        User user = new User(
                "delete@mail.com",
                "Имя",
                100
        );

        userRepository.save(user);

        long deletedRows = userRepository.deleteByEmail("delete@mail.com");

        boolean exists = userRepository.existsByEmail("delete@mail.com");

        assertFalse(exists);
        assertEquals(1L, deletedRows);
    }
}

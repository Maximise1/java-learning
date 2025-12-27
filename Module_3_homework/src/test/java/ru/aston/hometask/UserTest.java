package ru.aston.hometask;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import ru.aston.hometask.builder.User;

class UserTest {

    @Test
    void when_UserBuilt_then_shouldUseProvidedValues() {
        User user = new User.Builder("Alice")
                .age(30)
                .build();

        assertEquals("Alice", user.getName());
        assertEquals(30, user.getAge());
    }
}

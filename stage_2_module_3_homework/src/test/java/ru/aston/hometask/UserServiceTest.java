package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import ru.aston.hometask.dao.UserDao;
import ru.aston.hometask.model.User;
import ru.aston.hometask.service.UserDto;
import ru.aston.hometask.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao dao;
    private UserService service;
    @Captor
    ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp() {
        service = new UserService(dao);
    }

    @Test
    void when_getUserByIdCalled_then_ReturnsUserDto() {
        when(dao.getById(1L)).thenReturn(new User(
                "email@mail.ru",
                "Name",
                100,
                1L,
                LocalDateTime.now()
        ));

        UserDto user = service.getUserById(1L);

        assertNotNull(user);
        assertEquals(1L, (long) user.getId());
    }

    @Test
    void when_updateUserCalled_then_UserPassedToDao() {
        UserDto userDto = new UserDto(
                "test@mail.ru",
                100,
                "Name",
                1L,
                LocalDateTime.now()
        );

        service.updateUser(userDto);
        verify(dao).update(userCaptor.capture());
        User updatedUser = userCaptor.getValue();

        assertNotNull(updatedUser);
        assertEquals("Name", updatedUser.getName());
    }

    @Test
    void when_createUserCalled_then_UserPassedToDao() {
        UserDto userDto = new UserDto(
                "test@mail.ru",
                100,
                "Name"
        );

        service.createUser(userDto);
        verify(dao).create(userCaptor.capture());
        User userToCreate = userCaptor.getValue();

        assertNotNull(userToCreate);
        assertEquals("Name", userToCreate.getName());
    }
}

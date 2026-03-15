package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import java.util.Optional;

import ru.aston.hometask.dao.UserRepository;
import ru.aston.hometask.exceptions.UserNotFoundException;
import ru.aston.hometask.model.User;
import ru.aston.hometask.service.UserDto;
import ru.aston.hometask.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    private UserService service;
    @Captor
    ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp() {
        service = new UserService(repository);
    }

    @Test
    void when_getUserByIdCalled_then_ReturnsUserDto() {
        User user = new User(
                "email@mail.ru",
                "Name",
                100,
                LocalDateTime.now()
        );

        when(repository.findByEmail("email@mail.ru"))
                .thenReturn(Optional.of(user));

        UserDto result = service.getUserByEmail("email@mail.ru");

        assertNotNull(result);
        assertEquals("email@mail.ru", result.getMail());
        assertEquals("Name", result.getName());
    }

    @Test
    void when_updateUserCalled_then_UserPassedToDao() {
        UserDto dto = new UserDto(
                "test@mail.ru",
                100,
                "Name",
                LocalDateTime.now()
        );

        service.updateUser(dto);
        verify(repository).save(userCaptor.capture());
        User updatedUser = userCaptor.getValue();

        assertNotNull(updatedUser);
        assertEquals("Name", updatedUser.getName());
        assertEquals("test@mail.ru", updatedUser.getEmail());
    }

    @Test
    void when_createUserCalled_then_UserPassedToDao() {
        UserDto userDto = new UserDto(
                "test@mail.ru",
                100,
                "Name"
        );

        service.createUser(userDto);
        verify(repository).save(userCaptor.capture());
        User userToCreate = userCaptor.getValue();

        assertNotNull(userToCreate);
        assertEquals("Name", userToCreate.getName());
        assertEquals("test@mail.ru", userToCreate.getEmail());
    }

    @Test
    void when_deleteUserCalled_then_repositoryMethodCalled() {
        service.deleteUserByEmail("mail@test.com");

        verify(repository).deleteByEmail("mail@test.com");
    }

    @Test
    void when_userNotFound_then_exceptionThrown() {

        when(repository.findByEmail("missing@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.getUserByEmail("missing@mail.com")
        );
    }
}

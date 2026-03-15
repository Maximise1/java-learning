package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import ru.aston.hometask.repository.UserRepository;
import ru.aston.hometask.exceptions.UserNotFoundException;
import ru.aston.hometask.repository.model.User;
import ru.aston.hometask.service.dto.UserDto;
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
    void when_getUserByEmailCalled_then_ReturnsUserDto() {
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
    void when_userNotFound_then_exceptionThrown() {

        when(repository.findByEmail("missing@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.getUserByEmail("missing@mail.com")
        );
    }

    @Test
    void updateUser_whenUserNotFound_throwsUserNotFoundException() {
        UserDto userDto = new UserDto(
                "missing@mail.ru",
                100,
                "Name"
        );
        when(repository.findByEmail("missing@mail.ru")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> service.updateUser("missing@mail.ru", userDto));
    }

    @Test
    void when_createUserCalled_then_UserPassedToDao() {
        UserDto userDto = new UserDto(
                "test@mail.ru",
                100,
                "Name"
        );
        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        service.createUser(userDto);

        verify(repository).save(userCaptor.capture());
        User userToCreate = userCaptor.getValue();

        assertNotNull(userToCreate);
        assertEquals("Name", userToCreate.getName());
        assertEquals("test@mail.ru", userToCreate.getEmail());
    }

    @Test
    void when_deleteUserCalled_then_repositoryMethodCalled() {
        when(repository.deleteByEmail("mail@test.com")).thenReturn(1L);

        service.deleteUserByEmail("mail@test.com");

        verify(repository).deleteByEmail("mail@test.com");
    }
}

package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;

import ru.aston.hometask.service.UserDto;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.ui.ConsoleUi;

class ConsoleUiTest {

    @Mock
    private UserService userService;

    private ConsoleUi consoleUi;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    @Captor
    ArgumentCaptor<UserDto> captor = ArgumentCaptor.forClass(UserDto.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleUi = new ConsoleUi(userService);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void when_createUserCalled_then_userPassedToService() {
        String input = "create John 30 john@mail.com\nexit\n";
        provideInput(input);

        consoleUi.runApp();

        verify(userService).createUser(captor.capture());

        UserDto createdUser = captor.getValue();
        assertEquals("John", createdUser.getName());
        assertEquals(30, createdUser.getAge());
        assertEquals("john@mail.com", createdUser.getMail());
        assertTrue(outContent.toString().contains("Пользователь успешно создан."));
    }

    @Test
    void when_readUserCalled_then_userPrinted() {
        UserDto mockUser = new UserDto("john@mail.com", 30, "John", 1L, LocalDateTime.now());
        when(userService.getUserById(1L)).thenReturn(mockUser);

        String input = "read 1\nexit\n";
        provideInput(input);

        consoleUi.runApp();

        verify(userService).getUserById(1L);
        String output = outContent.toString();
        assertTrue(output.contains("User found: name = John"));
        assertTrue(output.contains("email = john@mail.com"));
    }

    @Test
    void when_updateUserCalled_then_userPassedToService() {
        UserDto mockUser = new UserDto("john@mail.com", 30, "John", 1L, LocalDateTime.now());
        when(userService.getUserById(1L)).thenReturn(mockUser);

        String input = "update 1 name Johnny\nexit\n";
        provideInput(input);

        consoleUi.runApp();

        verify(userService).updateUser(captor.capture());

        UserDto updated = captor.getValue();
        assertEquals("Johnny", updated.getName());
        assertTrue(outContent.toString().contains("Пользователь успешно обновлен."));
    }

    @Test
    void when_deleteUserCalled_then_idPassedToService() {
        String input = "delete 1\nexit\n";
        provideInput(input);

        consoleUi.runApp();

        verify(userService).deleteUserById(1L);
        assertTrue(outContent.toString().contains("Пользователь успешно удален."));
    }

    @Test
    void when_badCommandPassed_then_warningPrinted() {
        String input = "bad_command\nexit\n";
        provideInput(input);

        consoleUi.runApp();

        String output = outContent.toString();
        assertTrue(output.contains("Unrecognized command: bad_command"));
    }

    private void provideInput(String data) {
        InputStream testInput = new ByteArrayInputStream(data.getBytes());
        try {
            var scannerField = ConsoleUi.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(consoleUi, new Scanner(testInput));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

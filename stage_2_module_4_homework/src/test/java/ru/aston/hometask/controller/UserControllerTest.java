package ru.aston.hometask.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import ru.aston.hometask.exceptions.GlobalExceptionHandler;
import ru.aston.hometask.exceptions.UserNotFoundException;
import ru.aston.hometask.service.dto.UserDto;
import ru.aston.hometask.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void when_createUserCalled_then_returns201() throws Exception {
        UserDto dto = new UserDto("test@mail.com", 25, "Имя");

        mockMvc.perform(post("/users")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is(201));

        verify(userService).createUser(any(UserDto.class));
    }

    @Test
    void when_getUserCalled_then_returnsUserDto() throws Exception {
        UserDto dto = new UserDto("test@mail.com", 25, "Name", LocalDateTime.now());

        when(userService.getUserByEmail("test@mail.com")).thenReturn(dto);

        mockMvc.perform(get("/users/test@mail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mail").value("test@mail.com"))
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    void when_updateUserCalled_then_returns200() throws Exception {
        UserDto dto = new UserDto("test@mail.com", 25, "Name", LocalDateTime.now());

        mockMvc.perform(put("/users/test@mail.com")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq("test@mail.com"), any(UserDto.class));
    }

    @Test
    void when_deleteUserCalled_then_returns204() throws Exception {
        mockMvc.perform(delete("/users/test@mail.com"))
                .andExpect(status().is(204));

        verify(userService).deleteUserByEmail("test@mail.com");
    }

    @Test
    void when_userNotFound_then_returns404() throws Exception {
        when(userService.getUserByEmail("missing@mail.com"))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/users/missing@mail.com"))
                .andExpect(status().isNotFound());
    }
}

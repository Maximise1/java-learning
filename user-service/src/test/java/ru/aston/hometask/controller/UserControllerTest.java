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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;

import ru.aston.hometask.exceptions.UserNotFoundException;
import ru.aston.hometask.service.dto.UserDto;
import ru.aston.hometask.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserService userService;

    @Test
    void when_createUserCalled_then_returns201() throws Exception {
        UserDto dto = new UserDto("test@mail.com", 25, "Имя");

        when(userService.createUser(any(UserDto.class))).thenReturn(dto);

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

        when(userService.updateUser(any(String.class), any(UserDto.class))).thenReturn(dto);

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

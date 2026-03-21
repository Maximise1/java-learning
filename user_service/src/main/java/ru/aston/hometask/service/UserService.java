package ru.aston.hometask.service;

import ru.aston.hometask.service.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto dto);
    UserDto getUserByEmail(String email);
    UserDto updateUser(String email, UserDto dto);
    void deleteUserByEmail(String email);
}

package ru.aston.hometask.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.aston.hometask.service.UserDto;
import ru.aston.hometask.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody UserDto dto) {
        userService.createUser(dto);
    }

    @GetMapping("/{email}")
    public UserDto getUser(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping
    public void updateUser(
            //@PathVariable String email,
            @RequestBody UserDto dto
    ) {
        userService.updateUser(dto);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUserByEmail(email);
    }
}

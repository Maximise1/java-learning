package ru.aston.hometask.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.aston.hometask.service.dto.UserDto;
import ru.aston.hometask.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Добавить пользователя в базу данных")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь создан"),
            @ApiResponse(responseCode = "400", description = "Email уже используется")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto dto) {
        UserDto created = userService.createUser(dto);

        created.add(linkTo(methodOn(UserController.class).getUser(created.getMail())).withSelfRel());
        created.add(linkTo(methodOn(UserController.class)
                .updateUser(created.getMail(), null)).withRel("update"));
        created.add(linkTo(methodOn(UserController.class)
                .deleteUser(created.getMail())).withRel("delete"));

        return created;
    }

    @Operation(summary = "получить пользователя по переданному email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{email}")
    public UserDto getUser(@PathVariable String email) {
        UserDto dto = userService.getUserByEmail(email);

        dto.add(linkTo(methodOn(UserController.class).getUser(email)).withSelfRel());
        dto.add(linkTo(methodOn(UserController.class).updateUser(email, null)).withRel("update"));
        dto.add(linkTo(methodOn(UserController.class).deleteUser(email)).withRel("delete"));

        return dto;
    }

    @Operation(summary = "Обновить данные пользователя по переданному email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь обновлён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping("/{email}")
    public UserDto updateUser(
            @PathVariable String email,
            @RequestBody UserDto dto
    ) {
        UserDto updated = userService.updateUser(email, dto);

        updated.add(linkTo(methodOn(UserController.class).getUser(email)).withSelfRel());
        updated.add(linkTo(methodOn(UserController.class).deleteUser(email)).withRel("delete"));

        return updated;
    }

    @Operation(summary = "Удалить пользователя из базы данных по переданному email")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }
}

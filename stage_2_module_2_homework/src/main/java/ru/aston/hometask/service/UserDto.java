package ru.aston.hometask.service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import ru.aston.hometask.exceptions.UserValidationException;

public class UserDto {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private String mail;
    private Integer age;
    private String name;
    private LocalDateTime createdAt;

    public UserDto() {}

    public UserDto(String mail, Integer age, String name, LocalDateTime createdAt) {
        validateFields(mail, age, name);

        this.mail = mail;
        this.age = age;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UserDto(String mail, Integer age, String name) {
        validateFields(mail, age, name);

        this.mail = mail;
        this.age = age;
        this.name = name;
    }

    private void validateFields(String mail, Integer age, String name) {
        if (mail == null || !EMAIL_PATTERN.matcher(mail).matches()) {
            throw new UserValidationException("Incorrect email passed: " + mail);
        }
        if ((age != null) && (age < 0 || age > 150)) {
            throw new UserValidationException("Incorrect age passed: " + age);
        }
        if (name == null || name.isEmpty() || name.length() > 50) {
            throw new UserValidationException("Incorrect name passed: " + name);
        }
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getMail() {
        return mail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

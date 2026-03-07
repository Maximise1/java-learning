package ru.aston.hometask.service;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}

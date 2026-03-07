package ru.aston.hometask.ui;

public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String field) {
        super("Invalid field: " + field);
    }
}

package ru.aston.hometask.exceptions;

public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String field) {
        super("Invalid field: " + field);
    }
}

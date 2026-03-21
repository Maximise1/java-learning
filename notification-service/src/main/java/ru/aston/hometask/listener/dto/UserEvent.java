package ru.aston.hometask.listener.dto;

public record UserEvent(
        String email,
        String event
) {}

package ru.aston.hometask.listener.dto;

public record UserEvent(
        String email,
        UserEventType event
) {}

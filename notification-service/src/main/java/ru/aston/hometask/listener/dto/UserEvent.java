package ru.aston.hometask.listener.dto;

public record UserEvent(
        String email,
        UserEventType event
) {
    public UserEvent {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }
}

package ru.aston.hometask.service;

public interface EmailService {
    void sendCreatedEmail(String email);
    void sendDeletedEmail(String email);
}

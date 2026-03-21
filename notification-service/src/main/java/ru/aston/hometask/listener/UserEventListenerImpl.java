package ru.aston.hometask.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

import ru.aston.hometask.listener.dto.UserEvent;
import ru.aston.hometask.listener.dto.UserEventType;
import ru.aston.hometask.service.EmailService;

@Component
public class UserEventListenerImpl implements UserEventListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailService emailService;

    public UserEventListenerImpl(EmailService service) {
        this.emailService = service;
    }

    @Override
    @KafkaListener(topics = "user-events")
    public void handle(String message) {
        try {
            UserEvent event = objectMapper.readValue(message, UserEvent.class);

            if (Objects.equals(event.event(), UserEventType.CREATE.name())) {
                emailService.sendCreatedEmail(event.email());
            } else if (Objects.equals(event.event(), UserEventType.DELETE.name())) {
                emailService.sendDeletedEmail(event.email());
            }
        } catch (JsonProcessingException e) {
            System.out.println("Логи не настроены, но эксепшен пойман: " + e.getMessage());
        }
    }
}

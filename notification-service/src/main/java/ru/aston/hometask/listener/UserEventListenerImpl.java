package ru.aston.hometask.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.aston.hometask.listener.dto.UserEvent;
import ru.aston.hometask.listener.dto.UserEventType;
import ru.aston.hometask.listener.handlers.UserEventHandler;
import ru.aston.hometask.service.EmailService;

@Component
public class UserEventListenerImpl implements UserEventListener {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    private final Map<UserEventType, UserEventHandler> handlers;

    public UserEventListenerImpl(List<UserEventHandler> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(
                        UserEventHandler::getUserEventType,
                        handler -> handler
                ));
    }

    @Override
    @KafkaListener(topics = "user-events")
    public void handle(String message) {
        try {
            UserEvent event = objectMapper.readValue(message, UserEvent.class);

            // Рефактор по Борисову: https://youtu.be/61duchvKI6o?t=2240
            UserEventHandler handler = handlers.get(event.event());
            handler.handleEvent(event);

        } catch (JsonProcessingException e) {
            System.out.println("Логи не настроены, но эксепшен пойман: " + e.getMessage());
        }
    }
}

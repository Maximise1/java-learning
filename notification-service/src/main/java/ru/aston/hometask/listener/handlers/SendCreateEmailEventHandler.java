package ru.aston.hometask.listener.handlers;

import org.springframework.stereotype.Component;

import ru.aston.hometask.listener.dto.UserEvent;
import ru.aston.hometask.listener.dto.UserEventType;
import ru.aston.hometask.service.EmailService;

@Component
public class SendCreateEmailEventHandler implements UserEventHandler {

    private final EmailService service;

    public SendCreateEmailEventHandler(EmailService service) {
        this.service = service;
    }

    @Override
    public void handleEvent(UserEvent event) {
        if (event.event() != UserEventType.CREATE) {
            throw new IllegalArgumentException("Wrong event type passed");
        }

        service.sendCreatedEmail(event.email());
    }

    @Override
    public UserEventType getUserEventType() {
        return UserEventType.CREATE;
    }
}

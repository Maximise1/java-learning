package ru.aston.hometask.listener.handlers;

import org.springframework.stereotype.Component;

import ru.aston.hometask.listener.dto.UserEvent;
import ru.aston.hometask.listener.dto.UserEventType;
import ru.aston.hometask.service.EmailService;

@Component
public class SendDeleteEmailEventHandler implements UserEventHandler {

    private final EmailService service;

    public SendDeleteEmailEventHandler(EmailService service) {
        this.service = service;
    }

    @Override
    public void handleEvent(UserEvent event) {
        if (event.event() != UserEventType.DELETE) {
            throw new IllegalArgumentException("Wrong event type passed");
        }

        service.sendDeletedEmail(event.email());
    }

    @Override
    public UserEventType getUserEventType() {
        return UserEventType.DELETE;
    }
}

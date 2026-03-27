package ru.aston.hometask.listener.handlers;

import ru.aston.hometask.listener.dto.UserEvent;
import ru.aston.hometask.listener.dto.UserEventType;

public interface UserEventHandler {

    public void handleEvent(UserEvent event);
    public UserEventType getUserEventType();
}

package ru.aston.hometask.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

public interface UserEventListener {

    void handle(String message) throws JsonProcessingException;
}

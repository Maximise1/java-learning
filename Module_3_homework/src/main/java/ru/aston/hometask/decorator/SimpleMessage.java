package ru.aston.hometask.decorator;

public class SimpleMessage implements Message {

    @Override
    public String getText() {
        return "message";
    }
}

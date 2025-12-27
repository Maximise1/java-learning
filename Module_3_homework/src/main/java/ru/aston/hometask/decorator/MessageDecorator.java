package ru.aston.hometask.decorator;

public class MessageDecorator implements Message {
    private final Message message;

    public MessageDecorator(Message message) {
        this.message = message;
    }

    @Override
    public String getText() {
        return "Decorated " + message.getText();
    }
}

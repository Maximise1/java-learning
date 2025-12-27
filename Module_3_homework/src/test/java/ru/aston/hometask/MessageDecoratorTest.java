package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.aston.hometask.decorator.Message;
import ru.aston.hometask.decorator.MessageDecorator;
import ru.aston.hometask.decorator.SimpleMessage;

class MessageDecoratorTest {

    @Test
    void when_decoratorUsed_then_shouldPrintDecoratedString() {
        Message baseMessage = new SimpleMessage();
        Message decoratedMessage = new MessageDecorator(baseMessage);

        assertEquals("Decorated message", decoratedMessage.getText());
    }
}

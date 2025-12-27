package ru.aston.hometask;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import ru.aston.hometask.proxy.RealService;
import ru.aston.hometask.proxy.Service;
import ru.aston.hometask.proxy.ServiceProxy;

class ServiceProxyTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void when_ProxyUsed_then_ServiceProxyRunExecuted() {
        Service realService = new RealService();
        Service proxy = new ServiceProxy(realService);

        proxy.run();

        String expectedOutput =
                "Some code\n" +
                "Real service running\n" +
                "Some more code\n";

        assertEquals(expectedOutput, outContent.toString());
    }
}

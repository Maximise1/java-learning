package ru.aston.hometask;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import ru.aston.hometask.cor.LogLevel;
import ru.aston.hometask.cor.LoggingClient;

class LoggingClientTest {

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
    void when_LogLevelDebug_then_DEBUGPrinted() {
        LoggingClient client = new LoggingClient();

        client.log(LogLevel.DEBUG, "Debugging");

        String output = outContent.toString();

        assertTrue(output.contains("[DEBUG] Debugging"));
        assertFalse(output.contains("[INFO]"));
        assertFalse(output.contains("[WARN]"));
        assertFalse(output.contains("[ERROR]"));
    }

    @Test
    void when_LogLevelInfo_then_INFOPrinted() {
        LoggingClient client = new LoggingClient();

        client.log(LogLevel.INFO, "Started");

        String output = outContent.toString();

        assertTrue(output.contains("[INFO] Started"));
        assertFalse(output.contains("[DEBUG]"));
        assertFalse(output.contains("[WARN]"));
        assertFalse(output.contains("[ERROR]"));
    }

    @Test
    void when_LogLevelWarn_then_WARNPrinted() {
        LoggingClient client = new LoggingClient();

        client.log(LogLevel.WARN, "Low disk");

        String output = outContent.toString();

        assertTrue(output.contains("[WARN] Low disk"));
        assertFalse(output.contains("[DEBUG]"));
        assertFalse(output.contains("[INFO]"));
        assertFalse(output.contains("[ERROR]"));
    }

    @Test
    void when_LogLevelError_then_ERRORPrinted() {
        LoggingClient client = new LoggingClient();

        client.log(LogLevel.ERROR, "Crash");

        String output = outContent.toString();

        assertTrue(output.contains("[ERROR] Crash"));
        assertFalse(output.contains("[DEBUG]"));
        assertFalse(output.contains("[INFO]"));
        assertFalse(output.contains("[WARN]"));
    }
}

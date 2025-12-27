package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import ru.aston.hometask.adapter.OldTask;

class OldTaskTest {

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
    void when_adapterUsed_then_shouldCallOldMethod() {
        OldTask oldTask = new OldTask();

        oldTask.execute();

        assertEquals("Running old task\n", outContent.toString());
    }
}

package ru.aston.hometask.cor;

public class DebugLogger extends Logger {

    public DebugLogger() {
        this.level = LogLevel.DEBUG;
    }

    @Override
    protected boolean canHandle(LogLevel level) {
        return level == LogLevel.DEBUG;
    }

    @Override
    protected void write(String message) {
        System.out.println("[DEBUG] " + message);
    }
}

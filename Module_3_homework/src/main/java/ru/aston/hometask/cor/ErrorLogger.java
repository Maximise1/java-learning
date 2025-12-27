package ru.aston.hometask.cor;

public class ErrorLogger extends Logger {

    public ErrorLogger() {
        this.level = LogLevel.ERROR;
    }

    @Override
    protected boolean canHandle(LogLevel level) {
        return level == LogLevel.ERROR;
    }

    @Override
    protected void write(String message) {
        System.out.println("[ERROR] " + message);
    }
}

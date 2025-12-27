package ru.aston.hometask.cor;

public class InfoLogger extends Logger {

    public InfoLogger() {
        this.level = LogLevel.INFO;
    }

    @Override
    protected boolean canHandle(LogLevel level) {
        return level == LogLevel.INFO;
    }

    @Override
    protected void write(String message) {
        System.out.println("[INFO] " + message);
    }
}

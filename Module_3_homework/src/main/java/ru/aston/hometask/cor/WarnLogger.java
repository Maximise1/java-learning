package ru.aston.hometask.cor;

public class WarnLogger extends Logger {

    public WarnLogger() {
        this.level = LogLevel.WARN;
    }

    @Override
    protected boolean canHandle(LogLevel level) {
        return level == LogLevel.WARN;
    }

    @Override
    protected void write(String message) {
        System.out.println("[WARN] " + message);
    }
}

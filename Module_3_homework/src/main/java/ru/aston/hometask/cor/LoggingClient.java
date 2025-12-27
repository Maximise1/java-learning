package ru.aston.hometask.cor;

public class LoggingClient {

    Logger startingLogger;

    public LoggingClient() {
        startingLogger = new DebugLogger();
        Logger info = new InfoLogger();
        Logger warn = new WarnLogger();
        Logger error = new ErrorLogger();

        startingLogger.setNext(info);
        info.setNext(warn);
        warn.setNext(error);
    }

    public void log(LogLevel level, String message) {
        startingLogger.log(level, message);
    }
}

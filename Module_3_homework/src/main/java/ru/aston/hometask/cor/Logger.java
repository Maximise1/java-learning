package ru.aston.hometask.cor;

public abstract class Logger {

    protected LogLevel level;
    protected Logger next;

    public void setNext(Logger next) {
        this.next = next;
    }

    public void log(LogLevel level, String message) {

        if (canHandle(level)) {
            write(message);
        }

        if (next != null) {
            next.log(level, message);
        }
    }

    protected abstract boolean canHandle(LogLevel level);

    protected abstract void write(String message);
}

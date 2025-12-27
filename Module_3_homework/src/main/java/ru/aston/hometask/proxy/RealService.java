package ru.aston.hometask.proxy;

public class RealService implements Service {
    @Override
    public void run() {
        System.out.println("Real service running");
    }
}

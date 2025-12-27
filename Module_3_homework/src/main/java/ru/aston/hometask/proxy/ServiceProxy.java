package ru.aston.hometask.proxy;

public class ServiceProxy implements Service {
    private final Service realService;

    public ServiceProxy(Service realService) {
        this.realService = realService;
    }

    @Override
    public void run() {
        System.out.println("Some code");
        realService.run();
        System.out.println("Some more code");
    }
}

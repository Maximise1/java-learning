package ru.aston.hometask;

import java.util.Arrays;

import ru.aston.hometask.adapter.OldTask;
import ru.aston.hometask.adapter.TaskAdapter;
import ru.aston.hometask.builder.User;
import ru.aston.hometask.cor.LogLevel;
import ru.aston.hometask.cor.LoggingClient;
import ru.aston.hometask.decorator.Message;
import ru.aston.hometask.decorator.MessageDecorator;
import ru.aston.hometask.decorator.SimpleMessage;
import ru.aston.hometask.proxy.RealService;
import ru.aston.hometask.proxy.Service;
import ru.aston.hometask.proxy.ServiceProxy;
import ru.aston.hometask.strategy.BubbleSortingStrategy;
import ru.aston.hometask.strategy.MergeSortingStrategy;
import ru.aston.hometask.strategy.SortingContext;

public class Main {

    public static void main(String[] args) {
        strategy();
        chainOfResponsibility();
        builder();
        proxy();
        decorator();
        adapter();
    }

    public static void strategy() {
        System.out.println("===========================STRATEGY=============================");

        int[] array = {1, 34, 87, 42, 3, 5, 99, 43};
        SortingContext sortingContext = new SortingContext();

        sortingContext.setSortingStrategy(new BubbleSortingStrategy());
        System.out.println("Array sorted with Bubble sort: "
                + Arrays.toString(sortingContext.sortArray(array)));

        sortingContext.setSortingStrategy(new MergeSortingStrategy());
        System.out.println("Array sorted with merge sort: "
                + Arrays.toString(sortingContext.sortArray(array)));

        System.out.println("\n\n");
    }

    public static void chainOfResponsibility() {
        System.out.println("====================CHAIN OF RESPONSIBILITY====================");
        LoggingClient client = new LoggingClient();

        client.log(LogLevel.DEBUG, "Debugging application");
        client.log(LogLevel.INFO, "Application started");
        client.log(LogLevel.WARN, "Low disk space");
        client.log(LogLevel.ERROR, "Unhandled exception");

        System.out.println("\n\n");
    }

    public static void builder() {
        System.out.println("====================BUILDER====================");

        User user = new User.Builder("Name")
                .age(42)
                .build();

        System.out.println("Age:" + user.getAge() + " Name:" + user.getName());

        System.out.println("\n\n");
    }

    public static void proxy() {
        System.out.println("====================PROXY====================");

        Service service = new ServiceProxy(new RealService());
        service.run();

        System.out.println("\n\n");
    }

    public static void decorator() {
        System.out.println("====================DECORATOR====================");

        Message message = new SimpleMessage();
        message = new MessageDecorator(message);

        System.out.println(message.getText());

        System.out.println("\n\n");
    }

    public static void adapter() {
        System.out.println("====================ADAPTER====================");

        TaskAdapter task = new TaskAdapter(new OldTask());
        task.run();

        System.out.println("\n\n");
    }
}

package ru.aston.hometask.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public void runExample() {
        new Thread(this::operation1, "Thread1").start();
        new Thread(this::operation2, "Thread2").start();
    }

    private void operation1() {
        lock1.lock();

        System.out.println("Lock1 acquired, trying to acquire lock 2.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();

        System.out.println("Lock2 acquired.");
        System.out.println("Operation1 executed successfully.");

        lock1.unlock();
        lock2.unlock();
    }

    private void operation2() {
        lock2.lock();

        System.out.println("Lock2 acquired, trying to acquire lock 1.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock1.lock();

        System.out.println("Lock1 acquired.");
        System.out.println("Operation2 executed successfully.");

        lock1.unlock();
        lock2.unlock();
    }
}

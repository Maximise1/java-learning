package ru.aston.hometask.livelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLock {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public void runExample() {
        new Thread(this::operation1, "Thread1").start();
        new Thread(this::operation2, "Thread2").start();
    }

    private void operation1() {
        while(true) {
            try {
                lock1.tryLock(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Lock1 acquired, trying to acquire lock 2.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (lock2.tryLock()) {
                System.out.println("Lock2 acquired.");
            } else {
                System.out.println("Cannot acquire lock2, releasing lock1.");
                lock1.unlock();
                continue;
            }

            System.out.println("Operation1 executed successfully.");
            break;
        }
    }

    private void operation2() {
        while(true) {
            try {
                lock2.tryLock(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Lock2 acquired, trying to acquire lock 2.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (lock1.tryLock()) {
                System.out.println("Lock1 acquired.");
            } else {
                System.out.println("Cannot acquire lock1, releasing lock2.");
                lock2.unlock();
                continue;
            }

            System.out.println("Operation1 executed successfully.");
            break;
        }
    }
}

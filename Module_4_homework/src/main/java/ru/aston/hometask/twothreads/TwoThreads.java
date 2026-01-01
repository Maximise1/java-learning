package ru.aston.hometask.twothreads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TwoThreads {

    private final Lock lock = new ReentrantLock();
    private final Condition firstTurn = lock.newCondition();
    private final Condition secondTurn = lock.newCondition();

    private boolean isFirstTurn = true;

    public void runExample() {
        new Thread(() -> printNumber("1", true), "Thread1").start();
        new Thread(() -> printNumber("2", false), "Thread2").start();
    }

    private void printNumber(String number, boolean first) {
        Condition current;
        Condition next;

        if (first) {
            current = firstTurn;
            next = secondTurn;
        } else {
            current = secondTurn;
            next = firstTurn;
        }

        while (true) {
            lock.lock();
            try {
                while (isFirstTurn != first) {
                    current.await(); // Отпускает лок перед тем как пойти спать
                }

                //Thread.sleep(1000);

                System.out.println(number);
                isFirstTurn = !isFirstTurn;
                next.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }
}

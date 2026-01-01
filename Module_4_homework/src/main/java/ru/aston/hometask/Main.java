package ru.aston.hometask;

import ru.aston.hometask.deadlock.DeadLock;
import ru.aston.hometask.livelock.LiveLock;
import ru.aston.hometask.twothreads.TwoThreads;

public class Main {

    public static void main(String[] args) {
        //LiveLock liveLock = new LiveLock();
        //liveLock.runExample();

        //DeadLock deadLock = new DeadLock();
        //deadLock.runExample();

        TwoThreads twoThreads = new TwoThreads();
        twoThreads.runExample();
    }
}

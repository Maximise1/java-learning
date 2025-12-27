package ru.aston.hometask.adapter;

public class TaskAdapter {

    private final OldTask oldTask;

    public TaskAdapter(OldTask oldTask) {
        this.oldTask = oldTask;
    }

    public void run() {
        oldTask.execute();
    }
}

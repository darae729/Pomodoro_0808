package org.sp.tproject.main.view;

public class TodoItem {
    private int yy;
    private int mm;
    private int dd;
    private String task;
    private boolean complete;
    private String client;

    public TodoItem(int yy, int mm, int dd, String task, boolean complete, String client) {
        this.yy = yy;
        this.mm = mm;
        this.dd = dd;
        this.task = task;
        this.complete = complete;
        this.client = client;
    }

    public int getYear() {
        return yy;
    }

    public int getMonth() {
        return mm;
    }

    public int getDay() {
        return dd;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getClient() {
        return client;
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d: %s (Complete: %b)", yy, mm, dd, task, complete);
        //return String.format("%04d-%02d-%02d: %s (Client: %s, Complete: %b)", yy, mm, dd, task, client, complete);
    }
}
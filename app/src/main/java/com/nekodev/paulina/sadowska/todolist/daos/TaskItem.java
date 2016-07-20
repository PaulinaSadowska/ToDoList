package com.nekodev.paulina.sadowska.todolist.daos;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public class TaskItem {
    private String title;
    private int userId;
    private int id;
    private boolean completed;

    @Override
    public String toString() {
        return(title + " " + id + " " + userId);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    public String getTitle() {
        return title;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }
}

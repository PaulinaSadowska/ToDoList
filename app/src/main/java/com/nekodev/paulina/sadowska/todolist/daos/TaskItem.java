package com.nekodev.paulina.sadowska.todolist.daos;

import com.orm.SugarRecord;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public class TaskItem extends SugarRecord{
    private String title;
    private int userId;
    private boolean completed;


    public TaskItem(){}

    public TaskItem(String title, int userId, Long id, boolean isCompleted){
        this.title = title;
        this.userId = userId;
        this.setId(id);
        this.completed = isCompleted;
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
    public boolean isCompleted() {
        return completed;
    }

    public int getIdInt() {
        return (int)((long)getId());
    }
}

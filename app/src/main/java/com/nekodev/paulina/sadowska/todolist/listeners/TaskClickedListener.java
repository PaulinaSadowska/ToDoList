package com.nekodev.paulina.sadowska.todolist.listeners;

import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public interface TaskClickedListener {
    void taskClicked(TaskItem task);
}

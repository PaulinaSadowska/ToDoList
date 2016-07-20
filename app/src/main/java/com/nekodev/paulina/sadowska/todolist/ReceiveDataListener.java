package com.nekodev.paulina.sadowska.todolist;

import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;

import java.util.List;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public interface ReceiveDataListener {
    void dataReceived(List<TaskItem> items);
}

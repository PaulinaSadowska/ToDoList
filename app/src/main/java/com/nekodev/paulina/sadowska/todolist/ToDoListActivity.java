package com.nekodev.paulina.sadowska.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.listeners.TaskClickedListener;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ToDoListFragment usersFragment = new ToDoListFragment();
        if(savedInstanceState!=null) {
            usersFragment.setArguments(savedInstanceState);
        }
        usersFragment.setTaskClickedListener(new TaskClickedListener() {
            @Override
            public void taskClicked(TaskItem task) {
                Intent previewActivity = new Intent(getApplicationContext(), EditTaskActivity.class);
                startActivity(previewActivity);
            }
        });
        transaction.replace(R.id.activity_todo_list_fragment_container, usersFragment);
        transaction.commit();
    }
}

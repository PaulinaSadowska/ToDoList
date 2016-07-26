package com.nekodev.paulina.sadowska.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nekodev.paulina.sadowska.todolist.R;
import com.nekodev.paulina.sadowska.todolist.ToDoListFragment;
import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((findViewById(R.id.activity_todo_list_fragment_container) != null && savedInstanceState == null)
                || findViewById(R.id.activity_todo_list_fragment_container) == null) {

            TaskItem.deleteAll(TaskItem.class, "was_Modified = 0"); //delete all not modified items
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_todo_list_fragment_container, new ToDoListFragment()).commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}

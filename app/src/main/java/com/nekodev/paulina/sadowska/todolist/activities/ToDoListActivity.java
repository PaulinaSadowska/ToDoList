package com.nekodev.paulina.sadowska.todolist.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nekodev.paulina.sadowska.todolist.R;
import com.nekodev.paulina.sadowska.todolist.ToDoListFragment;

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
        transaction.replace(R.id.activity_todo_list_fragment_container, usersFragment);
        transaction.commit();
    }
}

package com.nekodev.paulina.sadowska.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nekodev.paulina.sadowska.todolist.R;
import com.nekodev.paulina.sadowska.todolist.ToDoListFragment;
import com.nekodev.paulina.sadowska.todolist.constants.Constants;
import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;

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

        if(savedInstanceState==null){
            TaskItem.deleteAll(TaskItem.class, "was_Modified = 0");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(Constants.ORIENTATION_CHANGED_KEY, Constants.ORIENTATION_CHANGED);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                //
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

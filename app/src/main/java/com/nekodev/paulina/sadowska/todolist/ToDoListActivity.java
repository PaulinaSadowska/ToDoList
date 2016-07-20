package com.nekodev.paulina.sadowska.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToDoListActivity extends AppCompatActivity {

    @BindView(R.id.todo_list_recycler_view)
    RecyclerView mRecyclerView;

    private ToDoListAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new ToDoListAdapter();
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @OnClick(R.id.todo_list_save)
    public void saveList()
    {
        recyclerViewAdapter.queryData();
    }
}

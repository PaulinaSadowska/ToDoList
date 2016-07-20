package com.nekodev.paulina.sadowska.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.listeners.TaskClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToDoListActivity extends AppCompatActivity {

    private static final int ITEM_COUNT_THRESHOLD = 10;
    @BindView(R.id.todo_list_recycler_view)
    RecyclerView mRecyclerView;

    private ToDoListAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (!recyclerViewAdapter.canQuery()) {
                        int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        if (lastVisibleItem > recyclerViewAdapter.getItemCount() - ITEM_COUNT_THRESHOLD){
                            recyclerViewAdapter.loadMoreData();
                        }
                    }
                }
            });
        }
        recyclerViewAdapter = new ToDoListAdapter();
        recyclerViewAdapter.setTaskClickedListener(new TaskClickedListener() {
            @Override
            public void taskClicked(TaskItem task) {
                Intent editTaskActivity = new Intent(getApplicationContext(), EditTaskActivity.class);
                startActivity(editTaskActivity);
            }
        });
        mRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.loadMoreData();
    }

    @OnClick(R.id.todo_list_save)
    public void saveList()
    {
        Toast.makeText(this, "SAVE", Toast.LENGTH_SHORT).show();
    }
}

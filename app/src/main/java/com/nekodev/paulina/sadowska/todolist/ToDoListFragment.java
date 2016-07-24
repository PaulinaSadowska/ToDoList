package com.nekodev.paulina.sadowska.todolist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nekodev.paulina.sadowska.todolist.activities.EditTaskActivity;
import com.nekodev.paulina.sadowska.todolist.constants.Constants;
import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.listeners.TaskClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Paulina Sadowska on 23.07.2016.
 */
public class ToDoListFragment extends Fragment {

    private static final int ITEM_COUNT_THRESHOLD = 10;
    @BindView(R.id.todo_list_recycler_view)
    RecyclerView mRecyclerView;

    private ToDoListAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.todo_list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                Intent previewActivity = new Intent(getActivity(), EditTaskActivity.class);
                previewActivity.putExtra(Constants.IntentExtra.TITLE_KEY, task.getTitle());
                previewActivity.putExtra(Constants.IntentExtra.USER_ID_KEY, task.getUserId());
                previewActivity.putExtra(Constants.IntentExtra.ID_KEY, task.getId());
                previewActivity.putExtra(Constants.IntentExtra.IS_COMPLETED_KEY, task.isCompleted());
                startActivity(previewActivity);
            }
        });
        mRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.loadMoreData();
    }

    @OnClick(R.id.todo_list_save)
    public void saveList()
    {
        TaskItem.deleteAll(TaskItem.class);
        Toast.makeText(getActivity(), "ALL ITEMS DELETED", Toast.LENGTH_SHORT).show();
    }

}

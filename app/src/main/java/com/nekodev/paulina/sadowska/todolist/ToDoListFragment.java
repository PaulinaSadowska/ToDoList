package com.nekodev.paulina.sadowska.todolist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.nekodev.paulina.sadowska.todolist.activities.EditTaskActivity;
import com.nekodev.paulina.sadowska.todolist.constants.Constants;
import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.dataaccess.DataSaver;
import com.nekodev.paulina.sadowska.todolist.listeners.SaveDataListener;
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
    @BindView(R.id.todo_list_filter_switch)
    Switch mFilterSwitch;

    private boolean showOnlyModified;

    private ToDoListAdapter recyclerViewAdapter;
    private RecyclerView.OnScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            showOnlyModified = getArguments().getBoolean(Constants.SHOW_ONLY_MODIFIED);
        }
        configureRecyclerView();
        configureFilterSwitch();
    }

    private void configureFilterSwitch() {
        mFilterSwitch.setChecked(showOnlyModified);
        mFilterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         if(isChecked != showOnlyModified) {
                                                             showOnlyModified = isChecked;
                                                             filterView(isChecked);
                                                         }
                                                     }
                                                 }
        );
    }

    private void filterView(boolean isChecked) {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.clearAdapter();
            if (!isChecked) {
                recyclerViewAdapter.loadMoreData();
                mRecyclerView.addOnScrollListener(scrollListener);
            } else {
                recyclerViewAdapter.loadModifiedData();
                if(scrollListener!=null) {
                    mRecyclerView.removeOnScrollListener(scrollListener);
                }
            }
        }
    }

    private void configureRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        scrollListener = configureOnScrollListener();


        recyclerViewAdapter = new ToDoListAdapter();
        recyclerViewAdapter.setTaskClickedListener(new TaskClickedListener() {
            @Override
            public void taskClicked(TaskItem task) {
                startPreviewActivity(task);
            }
        });
        mRecyclerView.setAdapter(recyclerViewAdapter);
        filterView(showOnlyModified);
    }

    private void startPreviewActivity(TaskItem task) {
        Intent previewActivity = new Intent(getActivity(), EditTaskActivity.class);
        previewActivity.putExtra(Constants.IntentExtra.TITLE_KEY, task.getTitle());
        previewActivity.putExtra(Constants.IntentExtra.USER_ID_KEY, task.getUserId());
        previewActivity.putExtra(Constants.IntentExtra.ID_KEY, task.getId());
        previewActivity.putExtra(Constants.IntentExtra.IS_COMPLETED_KEY, task.isCompleted());
        startActivityForResult(previewActivity, Constants.ActivityResults.EDIT_TASK_RESULT);
    }

    private RecyclerView.OnScrollListener configureOnScrollListener(){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerViewAdapter.canQuery()) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItem > recyclerViewAdapter.getItemCount() - ITEM_COUNT_THRESHOLD) {
                        recyclerViewAdapter.loadMoreData();
                    }
                }
            }
        };
    }

    @OnClick(R.id.todo_list_save)
    public void saveList() {
        DataSaver dataSaver = new DataSaver();
        dataSaver.setSaveListener(new SaveDataListener() {
            @Override
            public void dataSaved() {
                Toast.makeText(getActivity(), R.string.data_saved, Toast.LENGTH_SHORT).show();
            }
        });
        dataSaver.saveTasksToAPI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.ActivityResults.EDIT_TASK_RESULT && data != null) {
            if (data.hasExtra(Constants.IntentExtra.IS_COMPLETED_KEY)
                    && data.hasExtra(Constants.IntentExtra.TITLE_KEY)
                    && data.hasExtra(Constants.IntentExtra.ID_KEY)
                    && data.hasExtra(Constants.IntentExtra.USER_ID_KEY)) {
                Boolean isCompleted = data.getExtras().getBoolean(Constants.IntentExtra.IS_COMPLETED_KEY);
                String title = data.getStringExtra(Constants.IntentExtra.TITLE_KEY);
                Long taskId = data.getLongExtra(Constants.IntentExtra.ID_KEY, 0);
                int userId = data.getIntExtra(Constants.IntentExtra.USER_ID_KEY, 0);
                recyclerViewAdapter.replaceTask(new TaskItem(title, userId, taskId, isCompleted, true));
            }
        }
    }

    public boolean getShowIsModified(){
        return showOnlyModified;
    }
}

package com.nekodev.paulina.sadowska.todolist.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nekodev.paulina.sadowska.todolist.R;
import com.nekodev.paulina.sadowska.todolist.constants.Constants;
import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public class EditTaskActivity extends AppCompatActivity {

    @BindView(R.id.edit_task_title)
    TextView mTaskTitle;
    @BindView(R.id.edit_task_id)
    TextView mTaskId;
    @BindView(R.id.edit_task_user_id)
    TextView mTaskUserId;
    @BindView(R.id.edit_task_is_completed)
    CheckBox mTaskIsCompleted;

    private TaskItem taskItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ButterKnife.bind(this);
        fillWithData();
    }

    private void fillWithData() {
        if (getIntent().hasExtra(Constants.IntentExtra.IS_COMPLETED_KEY)
                && getIntent().hasExtra(Constants.IntentExtra.TITLE_KEY)
                && getIntent().hasExtra(Constants.IntentExtra.USER_ID_KEY)
                && getIntent().hasExtra(Constants.IntentExtra.ID_KEY))
        {
            Boolean isCompleted = (getIntent().getByteExtra(Constants.IntentExtra.IS_COMPLETED_KEY, (byte) 0) == 1);
            String title = getIntent().getStringExtra(Constants.IntentExtra.TITLE_KEY);
            Long taskId = getIntent().getLongExtra(Constants.IntentExtra.ID_KEY, 0);
            int userId = getIntent().getIntExtra(Constants.IntentExtra.USER_ID_KEY, 0);
            taskItem = new TaskItem(title, userId, taskId, isCompleted);
            fillLayoutWIthData(taskItem);
        }
    }

    private void fillLayoutWIthData(TaskItem taskItem) {
        mTaskTitle.setText(taskItem.getTitle());
        mTaskId.setText(getString(R.string.id_label, taskItem.getId()));
        mTaskUserId.setText(getString(R.string.user_id_label, taskItem.getUserId()));
        mTaskIsCompleted.setChecked(taskItem.isCompleted());
    }

    @OnClick(R.id.edit_task_save_button)
    public void saveList()
    {
        if(!dataHasChanged() || taskItem ==  null) {
            finish();
            return;
        }

        TaskItem task = getTaskData();
        task.save();
        finish();
    }

    private boolean dataHasChanged() {
        return !mTaskTitle.getText().toString().equals(taskItem.getTitle())
                || mTaskIsCompleted.isChecked() != taskItem.isCompleted();
    }

    private TaskItem getTaskData() {
        String title = mTaskTitle.getText().toString();
        boolean isCompleted = mTaskIsCompleted.isChecked();
        return new TaskItem(title, taskItem.getUserId(), taskItem.getId(), isCompleted);
    }
}

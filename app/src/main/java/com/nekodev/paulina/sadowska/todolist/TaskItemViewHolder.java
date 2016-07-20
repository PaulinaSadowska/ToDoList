package com.nekodev.paulina.sadowska.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.listeners.ItemClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public class TaskItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.task_item_completed)
    CheckBox taskCompleted;
    @BindView(R.id.task_item_title)
    TextView taskTitle;

    private ItemClickedListener itemClickedListener;

    public void fillWIthData(TaskItem task){
        taskTitle.setText(task.getTitle());
        taskCompleted.setChecked(task.isCompleted());
    }

    public void setTitle(String title){
        taskTitle.setText(title);
    }

    public void setIsCompleted(boolean isCompleted){
        taskCompleted.setChecked(isCompleted);
    }

    @Override
    public void onClick(View v) {
        if(itemClickedListener !=null){
            itemClickedListener.itemClicked(getAdapterPosition());
        }
    }

    public TaskItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    public void setItemClickedListener(ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }
}

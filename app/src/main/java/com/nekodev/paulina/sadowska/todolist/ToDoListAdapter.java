package com.nekodev.paulina.sadowska.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.dataaccess.DataProvider;
import com.nekodev.paulina.sadowska.todolist.listeners.CheckedChangedListener;
import com.nekodev.paulina.sadowska.todolist.listeners.ItemClickedListener;
import com.nekodev.paulina.sadowska.todolist.listeners.ReceiveDataListener;
import com.nekodev.paulina.sadowska.todolist.listeners.TaskClickedListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public class ToDoListAdapter extends RecyclerView.Adapter<TaskItemViewHolder> {

    private static final int DATA_PACKET_SIZE = 20;
    private List<TaskItem> tasks = new LinkedList<>();
    private boolean isLoading;
    private TaskClickedListener taskClickedListener;

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_todo, parent, false);
            return new TaskItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskItemViewHolder holder, int position) {
        holder.fillWIthData(tasks.get(position));
        holder.setItemClickedListener(new ItemClickedListener() {
            @Override
            public void itemClicked(int position) {
                if(taskClickedListener!=null) {
                    taskClickedListener.taskClicked(tasks.get(position));
                }
            }
        });
        holder.setCheckedChangedListener(new CheckedChangedListener() {
            @Override
            public void checkedChanged(int position, boolean isChecked) {
                TaskItem task = tasks.get(position);
                task.setCompleted(isChecked);
                task.setWasModified(true);
                TaskItem.save(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public void loadMoreData(){
        DataProvider provider = new DataProvider();
        provider.setReceiveListener(new ReceiveDataListener() {
            @Override
            public void dataReceived(List<TaskItem> items) {
                if(tasks!=null) {
                    tasks.addAll(items);
                    isLoading = false;
                    notifyDataSetChanged();
                }
            }
        });
        isLoading = true;
        provider.getItems(tasks.size(), tasks.size() + DATA_PACKET_SIZE);
    }

    public boolean canQuery() {
        return isLoading;
    }

    public void setTaskClickedListener(TaskClickedListener taskClickedListener) {
        this.taskClickedListener = taskClickedListener;
    }

    public void replaceTask(TaskItem taskItem) {
        int id = taskItem.getIdInt();
        if(id<tasks.size() && tasks.get(id-1).getIdInt() == id){
            tasks.set(id-1, taskItem);
            notifyItemChanged(id-1);
        }
    }

    public void clearAdapter(){
        tasks.clear();
        notifyDataSetChanged();
    }

    public void loadModifiedData() {
        tasks.clear();
        tasks = TaskItem.find(TaskItem.class, "was_Modified = 1");
        notifyDataSetChanged();
    }
}

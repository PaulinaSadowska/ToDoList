package com.nekodev.paulina.sadowska.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public class ToDoListAdapter extends RecyclerView.Adapter<TaskItemViewHolder> {

    private static final int DATA_PACKET_SIZE = 10;
    private List<TaskItem> tasks = new LinkedList<>();

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_todo, parent, false);
            return new TaskItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskItemViewHolder holder, int position) {
        holder.fillWIthData(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public void queryData(){
        DataProvider provider = new DataProvider();
        provider.setReceiveListener(new ReceiveDataListener() {
            @Override
            public void dataReceived(List<TaskItem> items) {
                if(tasks!=null) {
                    tasks.addAll(items);
                    notifyDataSetChanged();
                }
            }
        });
        provider.getItems(tasks.size(), tasks.size() + DATA_PACKET_SIZE);
    }
}

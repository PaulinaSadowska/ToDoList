package com.nekodev.paulina.sadowska.todolist.dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nekodev.paulina.sadowska.todolist.constants.Constants;
import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.daos.TasksListAPI;
import com.nekodev.paulina.sadowska.todolist.listeners.ReceiveDataListener;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public class DataProvider implements Callback<List<TaskItem>> {

    public void setReceiveListener(ReceiveDataListener receiveListener) {
        this.receiveListener = receiveListener;
    }

    private ReceiveDataListener receiveListener;


    public void getItems(int start, int end) {
        if (tryToFindLocally(start, end)) {
            return;
        }
        fetchFromApi(start, end);
    }

    private void fetchFromApi(int start, int end) {
        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Call<List<TaskItem>> call = retrofit.create(TasksListAPI.class).loadData(start, end);
        call.enqueue(this);
    }

    private boolean tryToFindLocally(int start, int end) {
        List<TaskItem> localTasks = getLocalData(start, end);
        if (localTasks != null && localTasks.size() >= (end - start - 1)) {
            if (receiveListener != null) {
                receiveListener.dataReceived(localTasks);
            }
            return true;
        }
        return false;
    }

    private List<TaskItem> getLocalData(int start, int end) {
        if (end < start)
            return null;

        List<TaskItem> tasks = new LinkedList<>();
        for (int i = 0; i < end - start; i++) {
            TaskItem task = TaskItem.findById(TaskItem.class, start + i);
            if (task != null)
                tasks.add(task);
        }
        return tasks;
    }

    @Override
    public void onResponse(Call<List<TaskItem>> call, Response<List<TaskItem>> response) {
        if (response.isSuccessful() && receiveListener != null) {
            List<TaskItem> modifiedTasks = getModifiedList(response.body());
            for (TaskItem item : modifiedTasks) {
                TaskItem.save(item);
            }
            receiveListener.dataReceived(modifiedTasks);
        }
    }

    private List<TaskItem> getModifiedList(List<TaskItem> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            int id = tasks.get(i).getIdInt();
            TaskItem modifiedTask = TaskItem.findById(TaskItem.class, id);
            if (modifiedTask != null)
                tasks.set(i, modifiedTask);
        }
        return tasks;
    }

    @Override
    public void onFailure(Call<List<TaskItem>> call, Throwable t) {
        t.printStackTrace();
        if(receiveListener!=null){
            receiveListener.onFailure();
        }
    }
}

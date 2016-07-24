package com.nekodev.paulina.sadowska.todolist.dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.daos.TasksListAPI;
import com.nekodev.paulina.sadowska.todolist.listeners.ReceiveDataListener;

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

    private static final String ADDRESS = "http://127.0.0.1:3000/";

    public void setReceiveListener(ReceiveDataListener receiveListener) {
        this.receiveListener = receiveListener;
    }

    private ReceiveDataListener receiveListener;


    public void getItems(int start, int end){
        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ADDRESS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Call<List<TaskItem>> call = retrofit.create(TasksListAPI.class).loadData(start, end);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<TaskItem>> call, Response<List<TaskItem>> response) {
        if(receiveListener!=null){
            receiveListener.dataReceived(getModifiedList(response.body()));
        }
    }

    private List<TaskItem> getModifiedList(List<TaskItem> tasks) {
        //Todo - WORKS ONLY FOR FIRST PACK OF DATA! :(
        List<TaskItem> modifiedTasks = TaskItem.listAll(TaskItem.class);
        for (TaskItem task : modifiedTasks) {
            int id = task.getIdInt();
            if(tasks.size() >= id) {
                if (tasks.get(id-1).getIdInt() == id) {
                    tasks.set(id-1, task);
                }
            }
        }
        return tasks;
    }

    @Override
    public void onFailure(Call<List<TaskItem>> call, Throwable t) {
        t.printStackTrace();
    }
}

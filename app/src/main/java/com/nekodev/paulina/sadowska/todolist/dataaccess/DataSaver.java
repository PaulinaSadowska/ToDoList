package com.nekodev.paulina.sadowska.todolist.dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nekodev.paulina.sadowska.todolist.daos.TaskItem;
import com.nekodev.paulina.sadowska.todolist.daos.TasksListAPI;
import com.nekodev.paulina.sadowska.todolist.listeners.SaveDataListener;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public class DataSaver implements Callback<ResponseBody> {

    private static final String ADDRESS = "http://127.0.0.1:3000/";
    private SaveDataListener saveListener;
    private int tasksCount;
    private int tasksListSize;


    public void saveTasks(List<TaskItem> tasks) {
        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ADDRESS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        tasksCount = 0;
        tasksListSize = tasks.size();
        for (TaskItem task : tasks) {
            Call<ResponseBody> call = retrofit.create(TasksListAPI.class).putSavedTasks(task.getIdInt(), task);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()) {
            tasksCount++;
            if (tasksCount == tasksListSize && saveListener != null) {
                {
                    saveListener.dataSaved();
                }
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }

    public void setSaveListener(SaveDataListener saveListener) {
        this.saveListener = saveListener;
    }
}

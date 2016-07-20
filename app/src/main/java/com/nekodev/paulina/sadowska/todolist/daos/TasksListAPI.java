package com.nekodev.paulina.sadowska.todolist.daos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public interface TasksListAPI {
    @GET("/todos/")
    Call<List<TaskItem>> loadData(@Query("_start") int start, @Query("_end") int end);
}

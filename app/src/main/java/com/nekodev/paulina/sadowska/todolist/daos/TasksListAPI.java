package com.nekodev.paulina.sadowska.todolist.daos;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Paulina Sadowska on 20.07.2016.
 */
public interface TasksListAPI {
    @GET("/todos/")
    Call<List<TaskItem>> loadData(@Query("_start") int start,
                                  @Query("_end") int end);

    @FormUrlEncoded
    @PUT("/todos/{id}")
    Call<ResponseBody> putSavedTasks(@Field("title") String title,
                                     @Field("userId") int userId,
                                     @Path("id") int id,
                                     @Field("completed") boolean completed);
}

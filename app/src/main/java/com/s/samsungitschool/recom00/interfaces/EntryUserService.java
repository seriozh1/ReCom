package com.s.samsungitschool.recom00.interfaces;

import com.s.samsungitschool.recom00.model.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EntryUserService {
    @POST("/")
    Call<User> getUser(@Query("login") String login);

    @POST("/")
    Call<String> authenticate(@Query("login") String login, @Query("password") String password);
}

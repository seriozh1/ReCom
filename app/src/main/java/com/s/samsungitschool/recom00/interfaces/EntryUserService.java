package com.s.samsungitschool.recom00.interfaces;

import com.s.samsungitschool.recom00.model.User;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EntryUserService {
    @POST("/user/get/{login}")
    Call<User> getUser(@Path("login") String login);

    @POST("/user/auth")
    Call<Object> authenticate(@Query("login") String login, @Query("password") String password);
}

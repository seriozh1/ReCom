package com.s.samsungitschool.recom00.interfaces;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RegisterUserService {
    @POST("/")
    Call<String> register(@Query("login") String login, @Query("password") String password, @Query("email") String email);
}

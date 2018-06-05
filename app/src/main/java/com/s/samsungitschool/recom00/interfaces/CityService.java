package com.s.samsungitschool.recom00.interfaces;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CityService {
    @POST("/city/send/{id}")
    Call<Object> sendComplaint(@Header("authString") String auth,
                               @Path("id") long id);
}

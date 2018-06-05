package com.s.samsungitschool.recom00.interfaces;

import android.graphics.Point;

import com.s.samsungitschool.recom00.model.ProblemPoint;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MapService {

    @POST("/map/point/add")
    Call<Object> addPoint(@Header("authString") String auth,
                        @Query("latitude") double latitude,
                        @Query("longitude") double longitude);

    @POST("/map/point/{id}/note/add")
    Call<Object> addNote(@Header("authString") String auth,
                         @Path("id") long id,
                         @Query("text") String text);

    @POST("/map/point/get")
    Call<ProblemPoint> getPointByLatAndLong(@Header("authString") String auth,
                                            @Query("latitude") double latitude,
                                            @Query("longitude") double longitude);

}

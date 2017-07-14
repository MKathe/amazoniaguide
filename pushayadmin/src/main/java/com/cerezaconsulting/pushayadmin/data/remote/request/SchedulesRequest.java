package com.cerezaconsulting.pushayadmin.data.remote.request;

import com.cerezaconsulting.pushayadmin.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by katherine on 4/07/17.
 */

public interface SchedulesRequest {

    @FormUrlEncoded
    @POST("createschedule/")
    Call<Void> createSchedules(@Header("Authorization") String token,
                               @Field("day_name") String day_name,
                               @Field("destiny_name") String destiny_name,
                               @Field("price_normal") float price_normal,
                               @Field("max_user") int max_user,
                               @Field("locality") String locality,
                               @Field("hour") String hour);

    @FormUrlEncoded
    @PUT("update/{pk}/schedule")
    Call<Void> editSchedules(@Header("Authorization") String token,
                             @Path("pk") int id,
                             @Field("price_normal") float price_normal,
                             @Field("max_user") int max_user);


    @DELETE("delete/{pk}/schedule/")
    Call<Void> deleteSchedules(@Header("Authorization") String token,
                               @Path("pk") int id);
}

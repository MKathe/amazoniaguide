package com.cerezaconsulting.pushayadmin.data.remote.request;

import com.cerezaconsulting.pushayadmin.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
                                          @Field("max_user") int max_user);
}

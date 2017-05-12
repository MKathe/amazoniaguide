package com.cerezaconsulting.pushay.data.remote.request;

import com.cerezaconsulting.pushay.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushay.data.entities.UserEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by katherine on 10/05/17.
 */

public interface LoginRequest {
    @FormUrlEncoded
    @POST()
    Call<AccessTokenEntity> login(@Field("email") String email, @Field("password") String password);

    @GET()
    Call<UserEntity> getUser(@Header("Authorization") String token);

}

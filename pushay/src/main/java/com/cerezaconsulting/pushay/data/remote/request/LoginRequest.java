package com.cerezaconsulting.pushay.data.remote.request;

import com.cerezaconsulting.pushay.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushay.data.entities.UserEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by katherine on 10/05/17.
 */

public interface LoginRequest {
    @FormUrlEncoded
    @POST("login/")
    Call<AccessTokenEntity> login(@Field("email") String email, @Field("password") String password);

    @GET("user/retrieve/")
    Call<UserEntity> getUser(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("login/mobile/facebook/")
    Call<AccessTokenEntity> loginUserFacebook(@Field("access_token") String tokenFace);

    @FormUrlEncoded
    @POST("user/recovery/")
    Call<UserEntity> recovery(@Field("email") String email);


    @FormUrlEncoded
    @PUT("user/change-password/")
    Call<UserEntity> changePassword(@Header("Authorization") String token,
                                    @Field("old_password") String old_password,
                                    @Field("new_password") String new_password,
                                    @Field("email") String email);

}

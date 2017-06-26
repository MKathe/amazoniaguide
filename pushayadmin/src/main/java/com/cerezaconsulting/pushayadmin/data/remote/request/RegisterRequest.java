package com.cerezaconsulting.pushayadmin.data.remote.request;

import com.cerezaconsulting.pushayadmin.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by katherine on 10/05/17.
 */

public interface RegisterRequest  {

    @FormUrlEncoded
    @POST("register-guide/")
    Call<AccessTokenEntity> registerUser(@Field("email") String email,
                                         @Field("password") String password,
                                         @Field("first_name") String first_name,
                                         @Field("last_name") String last_name,
                                         @Field("cellphone") String cellphone,
                                         @Field("gender")String gender);


    @FormUrlEncoded
    @PUT("user/update/")
    Call<UserEntity> editUser(@Header("Authorization") String token,
                              @Field("first_name") String first_name,
                              @Field("last_name") String last_name);

}


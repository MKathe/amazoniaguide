package com.cerezaconsulting.pushay.data.remote.request;

import com.cerezaconsulting.pushay.data.entities.AccessTokenEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by katherine on 24/07/17.
 */

public interface PaymentRequest {


    @FormUrlEncoded
    @POST("createreservation/")
    Call<Void> createReservation(@Field("num_coupons") int num_coupons, @Field("is_confirm") boolean is_confirm,
                                 @Field("name") String name);
}

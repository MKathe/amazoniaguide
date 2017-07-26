package com.cerezaconsulting.pushayadmin.data.remote.request;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by katherine on 25/07/17.
 */

public interface ReservationRequest {

    @FormUrlEncoded
    @PUT("update/{pk}/reservation/")
    Call<Void> updateWithQr(@Header("Authorization") String token,
                            @Path("pk") int id,
                            @Field("is_confirm") boolean is_confirm);


    @FormUrlEncoded
    @PUT("update/{code}/reservationwithcode/")
    Call<Void> updateWithCode(@Header("Authorization") String token,
                              @Path("code") String code,
                              @Field("is_confirm") boolean is_confirm);
}

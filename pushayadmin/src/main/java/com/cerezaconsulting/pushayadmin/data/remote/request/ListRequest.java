package com.cerezaconsulting.pushayadmin.data.remote.request;

import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by katherine on 12/06/17.
 */

public interface ListRequest {
    @GET("listcountry/")
    Call<TrackHolderEntity<CountryEntity>> getCountry(@Query("page") int numberPage);


    @GET("listcitybycountries/{pk}/")
    Call<TrackHolderEntity<CityEntity>> getCities(@Path("pk") int id,
                                                  @Query("page") int numberPage);

    @GET("listdestinybycities/{pk}/")
    Call<TrackHolderEntity<DestinyTravelEntity>> getDestiny(@Path("pk") int id,
                                                            @Query("page") int numberPage);

    @GET("myguidereservation/")
    Call<TrackHolderEntity<ReservationEntity>> getMyReservation(@Header("Authorization") String token,
                                                                @Query("page") int numberPage);

    @GET("list/reservation/novalidate/{pk}/")
    Call<TrackHolderEntity<ReservationEntity>> getNoValidateReservation(@Header("Authorization") String token,
                                                                        @Path("pk") int id,
                                                                        @Query("page") int numberPage);

    @GET("list/reservation/validate/")
    Call<TrackHolderEntity<ReservationEntity>> getValidateReservation(@Header("Authorization") String token,
                                                            @Query("page") int numberPage);


    @GET("myschedulebyday/")
    Call<TrackHolderEntity<SchedulesEntity>> getSchedules(@Header("Authorization") String token,
                                                          @Query("page") int numberPage);


    @GET("list/reservation/payment/goal/")
    Call<TrackHolderEntity<ReservationEntity>> getMyPaymentGoal(@Header("Authorization") String token,
                                                                      @Query("page") int numberPage);

    @GET("list/reservation/payment/pendient/")
    Call<TrackHolderEntity<ReservationEntity>> getMyPaymentPendient(@Header("Authorization") String token,
                                                                @Query("page") int numberPage);
}

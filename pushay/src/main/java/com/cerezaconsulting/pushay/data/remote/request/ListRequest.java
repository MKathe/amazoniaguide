package com.cerezaconsulting.pushay.data.remote.request;


import com.cerezaconsulting.pushay.data.entities.CityEntity;
import com.cerezaconsulting.pushay.data.entities.CountryEntity;
import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.data.entities.trackholder.TrackHolderEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("list/{date}/schedulebydate/")
    Call<TrackHolderEntity<SchedulesEntity>> getListSchedules(@Header("Authorization") String token,
                                                              @Path("date") String date,
                                                              @Query("search") String destinyName,
                                                              @Query("page") int numberPage);


    @GET("listschedulebydestinies/{destiny}/{date}/{num}/")
    Call<TrackHolderEntity<SchedulesEntity>> getListSchedulesInOrder(@Header("Authorization") String token,
                                                                     @Path("destiny") String destiny,
                                                                     @Path("date") String date,
                                                                     @Path("num") int num,
                                                                     @Query("page") int numberPage);


    @GET("myreservation/")
    Call<TrackHolderEntity<ReservationEntity>> getReservation(@Header("Authorization") String token,
                                                              @Query("page") int numberPage);

}

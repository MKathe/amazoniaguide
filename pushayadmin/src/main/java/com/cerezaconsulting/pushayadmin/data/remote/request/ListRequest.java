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
    Call<TrackHolderEntity<CountryEntity>> getCountry();


    @GET("listcitybycountries/{pk}/")
    Call<TrackHolderEntity<CityEntity>> getCities(@Path("pk") int id);

    @GET("listdestinybycities/{pk}/")
    Call<TrackHolderEntity<DestinyTravelEntity>> getDestiny(@Path("pk") int id);

    @GET("listreservation/")
    Call<TrackHolderEntity<ReservationEntity>> getReservation();

    @GET("myschedulebyday/")
    Call<TrackHolderEntity<SchedulesEntity>> getSchedules(@Header("Authorization") String token,
                                                          @Query("page") int numberPage);
}

package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DayEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.ListRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.LoginContract;
import com.cerezaconsulting.pushayadmin.presentation.contracts.TodayContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by katherine on 24/05/17.
 */

public class TodayPresenter implements TodayContract.Presenter, PlaceItem{

    private final TodayContract.View mView;
    private final SessionManager mSessionManager;
    private Context context;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public TodayPresenter(TodayContract.View mView, Context context) {
        this.mView = mView;
        this.mSessionManager = new SessionManager(context);
        this.mView.setPresenter(this);

    }

    @Override
    public void start() {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(1);

        }
    }

    @Override
    public void loadOrdersFromPage(int page) {
        loadList(mSessionManager.getUserToken(), page);

    }

    @Override
    public void loadFromNextPage() {

        if (currentPage > 0)
            loadList(mSessionManager.getUserToken(), currentPage);
    }

    @Override
    public void loadList(String token, final int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackHolderEntity<ReservationEntity>> reservation = listRequest.getReservation();
        reservation.enqueue(new Callback<TrackHolderEntity<ReservationEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<ReservationEntity>> call, Response<TrackHolderEntity<ReservationEntity>> response) {
                mView.setLoadingIndicator(false);
                if (!mView.isActive()) {
                    return;
                }
                if (response.isSuccessful()) {

                    if (response.body().getNext() != null) {
                        currentPage = page +1;
                    } else {
                        currentPage = -1;
                    }
                    mView.getTodayList(response.body().getResults());

                } else {
                    mView.showErrorMessage("Error al obtener la lista");
                }
            }

            @Override
            public void onFailure(Call<TrackHolderEntity<ReservationEntity>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }



    @Override
    public void clickItem(ReservationEntity reservationEntity) {
        mView.showDetailsTravel(reservationEntity);
    }

    @Override
    public void deleteItem(ReservationEntity reservationEntity, int position) {

    }
}

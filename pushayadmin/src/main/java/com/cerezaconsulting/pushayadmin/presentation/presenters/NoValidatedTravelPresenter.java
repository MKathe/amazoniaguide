package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.ListRequest;
import com.cerezaconsulting.pushayadmin.data.remote.request.ReservationRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.NoValidatedTravelContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by katherine on 24/05/17.
 */

public class NoValidatedTravelPresenter implements NoValidatedTravelContract.Presenter, PlaceItem{

    private final NoValidatedTravelContract.View mView;
    private final SessionManager mSessionManager;
    private Context context;
    private boolean firstLoad = false;
    private int currentPage = 1;
    private int newId;

    public NoValidatedTravelPresenter(NoValidatedTravelContract.View mView, Context context) {
        this.mView = mView;
        this.mSessionManager = new SessionManager(context);
        this.mView.setPresenter(this);

    }

    @Override
    public void start() { }

    @Override
    public void startLoad(int id) {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(id, 1);
        }
    }

    @Override
    public void loadOrdersFromPage(int id, int page) {
        loadListTravel(id, page);

    }

    @Override
    public void loadFromNextPage(int id) {

        if (currentPage > 0){
                    loadListTravel(id, currentPage);

        }
    }



    @Override
    public void loadListTravel(final int id, final int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        final Call<TrackHolderEntity<ReservationEntity>> reservation = listRequest.getNoValidateReservation("Token "+mSessionManager.getUserToken(), id , page);
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
                    newId = id;
                    mView.getListTravel(response.body().getResults());


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
    public void validatedTravelWithCode(String code, boolean is_confirm) {
        mView.setLoadingIndicator(true);
        ReservationRequest reservationRequest = ServiceFactory.createService(ReservationRequest.class);
        final Call<Void> reservation = reservationRequest.updateWithCode("Token "+mSessionManager.getUserToken(), code , is_confirm);
        reservation.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {
                    mView.showDetailsValidate("El viaje ha sido validado con éxito");


                } else {
                    mView.showErrorMessage("Error al obtener la lista");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }

    @Override
    public void validatedTravelWithQr(int id, boolean is_confirm) {
        mView.setLoadingIndicator(true);
        ReservationRequest reservationRequest = ServiceFactory.createService(ReservationRequest.class);
        final Call<Void> reservation = reservationRequest.updateWithQr("Token "+mSessionManager.getUserToken(), id, is_confirm);
        reservation.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {
                    mView.showDetailsValidate("El viaje ha sido validado con éxito");


                } else {
                    mView.showErrorMessage("Error al obtener la lista");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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
        switch (newId){
            case 1:
                break;
            case 2:
                mView.showDetailsTravel(reservationEntity);
                break;
            case 3:
                break;
        }
    }

    @Override
    public void deleteItem(ReservationEntity reservationEntity, int position) {

    }
}

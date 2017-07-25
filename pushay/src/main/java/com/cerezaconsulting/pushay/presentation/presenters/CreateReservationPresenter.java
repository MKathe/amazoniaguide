package com.cerezaconsulting.pushay.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushay.data.entities.CityEntity;
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;
import com.cerezaconsulting.pushay.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.data.remote.ServiceFactory;
import com.cerezaconsulting.pushay.data.remote.request.ListRequest;
import com.cerezaconsulting.pushay.data.remote.request.PaymentRequest;
import com.cerezaconsulting.pushay.presentation.contracts.CitiesContract;
import com.cerezaconsulting.pushay.presentation.contracts.CreateReservationContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.CitiesItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 28/06/17.
 */

public class CreateReservationPresenter implements CreateReservationContract.Presenter {

    private CreateReservationContract.View mView;
    private Context context;
    private SessionManager mSessionManager;


    public CreateReservationPresenter(CreateReservationContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {

    }
    @Override
    public void createReservation(int num_coupons, boolean is_confirm, String name) {
        mView.setLoadingIndicator(true);
        PaymentRequest paymentRequest = ServiceFactory.createService(PaymentRequest.class);
        Call<ReservationEntity> orders = paymentRequest.createReservation("Token "+mSessionManager.getUserToken(),num_coupons,is_confirm,name);
        orders.enqueue(new Callback<ReservationEntity>() {
            @Override
            public void onResponse(Call<ReservationEntity> call, Response<ReservationEntity> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.createReservationResponse("Se ha comprado el ticket con éxito");

                } else {
                    mView.showErrorMessage("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<ReservationEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }
}

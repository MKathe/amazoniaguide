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
    public void createReservation(String token, int num_coupons, boolean is_confirm, String name, String date) {
        mView.setLoadingIndicator(true);
        PaymentRequest paymentRequest = ServiceFactory.createService(PaymentRequest.class);
        Call<Void> orders = paymentRequest.createReservation("Token "+token, num_coupons, is_confirm, name, date);
        orders.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mView.setLoadingIndicator(false);
                if (!mView.isActive()) {
                    return;
                }
                if (response.isSuccessful()) {

                    mView.createReservationResponse("Tu ticket se ha comprado con éxito");

                } else {

                    mView.showErrorMessage("Error al obtener las órdenes");
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
}

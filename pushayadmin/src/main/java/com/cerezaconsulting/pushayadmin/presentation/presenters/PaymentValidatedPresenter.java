package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.ListRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.PaymentContract;
import com.cerezaconsulting.pushayadmin.presentation.contracts.ValidatedTravelContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 24/05/17.
 */

public class PaymentValidatedPresenter implements PaymentContract.Presenter, PlaceItem{

    private final PaymentContract.View mView;
    private final SessionManager mSessionManager;
    private Context context;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public PaymentValidatedPresenter(PaymentContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);

    }

    @Override
    public void start() {

    }

    @Override
    public void startLoad(int id) {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(id,1);
        }
    }

    @Override
    public void loadOrdersFromPage(int id, int page) {
        switch (id){
            case 1:
                getListPaymentGoal(page);
                break;
            case 2:
                getListPaymentPendient(page);
                break;
        }

    }

    @Override
    public void loadFromNextPage(int id) {

        if (currentPage > 0)
            switch (id){
                case 1:
                    getListPaymentGoal(currentPage);
                    break;
                case 2:
                    getListPaymentPendient(currentPage);
                    break;
            }
    }

    @Override
    public void getListPaymentPendient(final int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        final Call<TrackHolderEntity<ReservationEntity>> reservation = listRequest.getMyPaymentPendient("Token "+mSessionManager.getUserToken(), page);
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
                    mView.getListPayment(response.body().getResults());

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
    public void getListPaymentGoal(final int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        final Call<TrackHolderEntity<ReservationEntity>> reservation = listRequest.getMyPaymentGoal("Token "+mSessionManager.getUserToken(), page);
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
                    mView.getListPayment(response.body().getResults());

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
        mView.clickItemPayment(reservationEntity);
    }

    @Override
    public void deleteItem(ReservationEntity reservationEntity, int position) {

    }

}

package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.ListRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.RegisterContract;
import com.cerezaconsulting.pushayadmin.presentation.contracts.ScheduleContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by katherine on 23/06/17.
 */

public class SchedulesPresenter implements ScheduleContract.Presenter, PlaceItem {
    private ScheduleContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private boolean firstLoad = false;
    private int currentPage = 1;



    public SchedulesPresenter(ScheduleContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
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
        Call<TrackHolderEntity<SchedulesEntity>> orders = listRequest.getSchedules("Token " + token, page);
        orders.enqueue(new Callback<TrackHolderEntity<SchedulesEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<SchedulesEntity>> call, Response<TrackHolderEntity<SchedulesEntity>> response) {
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
                    mView.getSchedules(response.body().getResults());

                } else {
                    mView.showErrorMessage("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<TrackHolderEntity<SchedulesEntity>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }

    @Override
    public void start() {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(1);

        }
    }

    @Override
    public void clickItem(ReservationEntity reservationEntity) {

    }

    @Override
    public void deleteItem(ReservationEntity reservationEntity, int position) {

    }
}

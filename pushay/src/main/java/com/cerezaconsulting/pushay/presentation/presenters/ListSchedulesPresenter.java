package com.cerezaconsulting.pushay.presentation.presenters;

import android.content.Context;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.data.remote.ServiceFactory;
import com.cerezaconsulting.pushay.data.remote.request.ListRequest;
import com.cerezaconsulting.pushay.presentation.contracts.ListSchedulesContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.SchedulesItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by katherine on 31/05/17.
 */

public class ListSchedulesPresenter implements ListSchedulesContract.Presenter, SchedulesItem {

    private final ListSchedulesContract.View mView;
    private final SessionManager mSessionManager;
    private Context context;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public ListSchedulesPresenter(ListSchedulesContract.View mView, Context context) {
        this.mView = mView;
        this.mSessionManager = new SessionManager(context);
        this.mView.setPresenter(this);

    }


    @Override
    public void loadOrdersFromPage(int page) {

    }

    @Override
    public void loadfromNextPage() {

    }

    @Override
    public void loadList(int id) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackHolderEntity<SchedulesEntity>> orders = listRequest.getListSchedules(id);
        orders.enqueue(new Callback<TrackHolderEntity<SchedulesEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<SchedulesEntity>> call, Response<TrackHolderEntity<SchedulesEntity>> response) {
                mView.setLoadingIndicator(false);
                if (!mView.isActive()) {
                    return;
                }
                if (response.isSuccessful()) {

                    mView.getListSchedulesByDay(response.body().getResults());

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

    }

    @Override
    public void clickItem(SchedulesEntity schedulesEntity) {

    }

    @Override
    public void deleteItem(SchedulesEntity schedulesEntity, int position) {

    }
}

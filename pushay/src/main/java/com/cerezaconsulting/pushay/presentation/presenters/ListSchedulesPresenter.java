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

import static com.google.common.base.Preconditions.checkNotNull;


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
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);

    }


    @Override
    public void start() {

    }

    @Override
    public void clickItem(SchedulesEntity schedulesEntity) {
        mView.showDetailsTickets(schedulesEntity);
    }

    @Override
    public void deleteItem(SchedulesEntity schedulesEntity, int position) {

    }

    @Override
    public void loadOrdersFromPage(String destinyName, String date, int page) {
        getListGuideByDestiny(destinyName,date,page);
    }

    @Override
    public void loadfromNextPage(String destinyName, String date) {

        if (currentPage > 0)
            getListGuideByDestiny(destinyName, date, currentPage);
    }

    @Override
    public void startLoad(String destinyName, String date) {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(destinyName, date, 1);
        }
    }

    @Override
    public void getListGuideByDestiny(String destinyName, String date, final int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackHolderEntity<SchedulesEntity>> orders = listRequest.getListSchedules("Token "+ mSessionManager.getUserToken(),
                date, destinyName, page);
        orders.enqueue(new Callback<TrackHolderEntity<SchedulesEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<SchedulesEntity>> call, Response<TrackHolderEntity<SchedulesEntity>> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    if (response.body().getNext() != null) {
                        currentPage = page +1;
                    } else {
                        currentPage = -1;
                    }
                    mView.getListGuideByDestiny(response.body().getResults());

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
    public void getListGuideInOrder(String destinyName, String date, int num, int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackHolderEntity<SchedulesEntity>> orders = listRequest.getListSchedulesInOrder("Token "+ mSessionManager.getUserToken(), destinyName,
                date, num, page);
        orders.enqueue(new Callback<TrackHolderEntity<SchedulesEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<SchedulesEntity>> call, Response<TrackHolderEntity<SchedulesEntity>> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.getListGuideByDestiny(response.body().getResults());

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
}

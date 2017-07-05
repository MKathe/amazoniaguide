package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.ListRequest;
import com.cerezaconsulting.pushayadmin.data.remote.request.SchedulesRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.CitiesContract;
import com.cerezaconsulting.pushayadmin.presentation.contracts.DestinyContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.DestinyItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 3/07/17.
 */

public class DestinyPresenter implements DestinyContract.Presenter, DestinyItem{

    private DestinyContract.View mView;
    private Context context;
    private SessionManager mSessionManager;

    public DestinyPresenter(DestinyContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }

    @Override
    public void listDestiny(int id) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackHolderEntity<DestinyTravelEntity>> orders = listRequest.getDestiny(id);
        orders.enqueue(new Callback<TrackHolderEntity<DestinyTravelEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<DestinyTravelEntity>> call, Response<TrackHolderEntity<DestinyTravelEntity>> response) {
                mView.setLoadingIndicator(false);
                if (!mView.isActive()) {
                    return;
                }
                if (response.isSuccessful()) {

                    mView.getDestiny(response.body().getResults());

                } else {
                    mView.showErrorMessage("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<TrackHolderEntity<DestinyTravelEntity>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }

    @Override
    public void createSchedules(SchedulesEntity schedulesEntity) {
        mView.setLoadingIndicator(true);
        SchedulesRequest schedulesRequest = ServiceFactory.createService(SchedulesRequest.class);
        Call<Void> orders = schedulesRequest.createSchedules("Token " + mSessionManager.getUserToken(),
                schedulesEntity.getDay_name(), schedulesEntity.getDestiny_name(),
                schedulesEntity.getPrice_normal(), schedulesEntity.getMax_user());
        orders.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mView.setLoadingIndicator(false);
                if (!mView.isActive()) {
                    return;
                }
                if (response.isSuccessful()) {

                    mView.createScheduleSuccesful("Horario Creado con éxito");
                } else {
                    mView.showErrorMessage("Error al crear el horario");
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
    public void start() {

    }

    @Override
    public void clickItem(DestinyTravelEntity destinyTravelEntity) {
        mView.clickItemDestiny(destinyTravelEntity);
    }

    @Override
    public void deleteItem(DestinyTravelEntity destinyTravelEntity, int position) {

    }
}

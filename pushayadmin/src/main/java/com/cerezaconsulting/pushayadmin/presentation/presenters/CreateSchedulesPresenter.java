package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.SchedulesRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.CreateSchedulesContract;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 12/07/17.
 */

public class CreateSchedulesPresenter implements CreateSchedulesContract.Presenter {

    private CreateSchedulesContract.View mView;
    private Context context;
    private SessionManager mSessionManager;

    public CreateSchedulesPresenter(CreateSchedulesContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }

    @Override
    public void start() {

    }

    @Override
    public void createSchedules(SchedulesEntity schedulesEntity) {
        mView.setLoadingIndicator(true);
        SchedulesRequest schedulesRequest = ServiceFactory.createService(SchedulesRequest.class);
        Call<Void> orders = schedulesRequest.createSchedules("Token " + mSessionManager.getUserToken(),
                schedulesEntity.getDay_name(), schedulesEntity.getDestiny_name(),
                schedulesEntity.getPrice_normal(), schedulesEntity.getMax_user(), schedulesEntity.getLocality(),
                schedulesEntity.getHour());
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
}

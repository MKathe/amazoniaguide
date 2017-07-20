package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.ListRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.TodayContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by katherine on 24/05/17.
 */

public class ComingSoonPresenter implements TodayContract.Presenter, PlaceItem{

    private final TodayContract.View mView;
    private final SessionManager mSessionManager;
    private Context context;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public ComingSoonPresenter(TodayContract.View mView, Context context) {
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
        final Call<TrackHolderEntity<ReservationEntity>> reservation = listRequest.getMyReservation("Token "+mSessionManager.getUserToken(), page);
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
                    getTravelList(response.body().getResults());

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

    public void getTravelList(ArrayList<ReservationEntity> list){

        ArrayList<ReservationEntity> newList = new ArrayList<>();
        /*Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd'/'MMMM'/'yyyy", new Locale("es","ES"));
        String today = format.format(date);

        System.out.println(today);
        System.out.println(list.get(1).getDay());*/

        for (int i = 0; i <list.size() ; i++) {

            if(!list.get(i).is_confirm() && !list.get(i).isEquals()){
                newList.add(list.get(i));
            }
        }
        mView.getTodayList(newList);
    }
    @Override
    public void clickItem(ReservationEntity reservationEntity) {
        mView.showDetailsTravel(reservationEntity);
    }

    @Override
    public void deleteItem(ReservationEntity reservationEntity, int position) {

    }
}

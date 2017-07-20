package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.ListRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.CitiesContract;
import com.cerezaconsulting.pushayadmin.presentation.contracts.CountriesContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.CitiesItem;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.CountriesItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 28/06/17.
 */

public class CitiesPresenter implements CitiesContract.Presenter, CitiesItem {

    private CitiesContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public CitiesPresenter(CitiesContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {

    }


    @Override
    public void clickItem(CityEntity cityEntity) {
        mView.clickItemCities(cityEntity);
    }

    @Override
    public void deleteItem(CityEntity cityEntity, int position) {

    }

    @Override
    public void loadOrdersFromPage(int id, int page) {
        getCities(id, page);

    }

    @Override
    public void loadfromNextPage(int id) {

        if (currentPage > 0)
            getCities(id, currentPage);

    }

    @Override
    public void startLoad(int id) {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(id, 1);
        }
    }

    @Override
    public void getCities(int id, final int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackHolderEntity<CityEntity>> orders = listRequest.getCities(id, page);
        orders.enqueue(new Callback<TrackHolderEntity<CityEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<CityEntity>> call, Response<TrackHolderEntity<CityEntity>> response) {

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
                    mView.getCities(response.body().getResults());

                } else {
                    mView.showErrorMessage("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<TrackHolderEntity<CityEntity>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }
}

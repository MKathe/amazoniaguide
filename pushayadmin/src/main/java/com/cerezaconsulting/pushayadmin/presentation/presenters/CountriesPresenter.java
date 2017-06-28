package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.ListRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.CountriesContract;
import com.cerezaconsulting.pushayadmin.presentation.contracts.ScheduleContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.CitiesItem;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.CountriesItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 28/06/17.
 */

public class CountriesPresenter implements CountriesContract.Presenter, CountriesItem {

    private CountriesContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public CountriesPresenter(CountriesContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }
    @Override
    public void loadOrdersFromPage(int page) {

    }

    @Override
    public void loadfromNextPage() {
    }

    @Override
    public void getPlaces() {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackHolderEntity<CountryEntity>> orders = listRequest.getCountry();
        orders.enqueue(new Callback<TrackHolderEntity<CountryEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<CountryEntity>> call, Response<TrackHolderEntity<CountryEntity>> response) {
                mView.setLoadingIndicator(false);
                if (!mView.isActive()) {
                    return;
                }
                if (response.isSuccessful()) {

                    if (response.body().getNext() != null) {
                    } else {
                        currentPage = -1;
                    }
                    mView.getCountries(response.body().getResults());

                } else {
                    mView.showErrorMessage("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<TrackHolderEntity<CountryEntity>> call, Throwable t) {
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
        getPlaces();
    }


    @Override
    public void clickItem(CountryEntity countryEntity) {

    }

    @Override
    public void deleteItem(CountryEntity countryEntity, int position) {

    }
}

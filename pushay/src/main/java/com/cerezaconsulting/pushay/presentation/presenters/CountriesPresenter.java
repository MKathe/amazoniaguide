package com.cerezaconsulting.pushay.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushay.data.entities.CountryEntity;
import com.cerezaconsulting.pushay.data.entities.trackholder.TrackHolderEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.data.remote.ServiceFactory;
import com.cerezaconsulting.pushay.data.remote.request.ListRequest;
import com.cerezaconsulting.pushay.presentation.contracts.CountriesContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.CountriesItem;

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
        getCountries(page);
    }

    @Override
    public void loadFromNextPage() {
        if (currentPage > 0)
            getCountries(currentPage);
    }

    @Override
    public void getCountries(final int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackHolderEntity<CountryEntity>> orders = listRequest.getCountry(page);
        orders.enqueue(new Callback<TrackHolderEntity<CountryEntity>>() {
            @Override
            public void onResponse(Call<TrackHolderEntity<CountryEntity>> call, Response<TrackHolderEntity<CountryEntity>> response) {
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
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(1);
        }
    }


    @Override
    public void clickItem(CountryEntity countryEntity) {
        mView.clickItemCountry(countryEntity);
    }

    @Override
    public void deleteItem(CountryEntity countryEntity, int position) {

    }
}

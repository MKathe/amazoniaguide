package com.cerezaconsulting.pushay.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushay.data.entities.PlacesEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.presentation.contracts.CountriesContract;
import com.cerezaconsulting.pushay.presentation.contracts.RegisterContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;

/**
 * Created by katherine on 15/05/17.
 */

public class CountriesPresenter implements CountriesContract.Presenter, PlaceItem {

    private CountriesContract.View mView;
    private Context context;
    private SessionManager mSessionManager;


    public CountriesPresenter(CountriesContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
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
    public void start() {

    }

    public void getPlaces(){

        ArrayList<PlacesEntity> list = new ArrayList<>();
        list.add(new PlacesEntity(1,"Lugar","Lugar"));
        list.add(new PlacesEntity(2,"aoisjdoand","asnfpasna"));
        list.add(new PlacesEntity(3,"aoisjdoand","asnfpasna"));
        list.add(new PlacesEntity(4,"aoisjdoand","asnfpasna"));
        list.add(new PlacesEntity(5,"aoisjdoand","asnfpasna"));
        mView.getCountries(list);



    }

    @Override
    public void clickItem(PlacesEntity placesEntity) {
        mView.showMessage("conexión API");
    }

    @Override
    public void deleteItem(PlacesEntity placesEntity, int position) {

    }
}

package com.cerezaconsulting.pushay.presentation.contracts;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.PlacesEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 2/06/17.
 */

public interface CitiesContract {
    interface View extends BaseView<Presenter> {

        void getCities(ArrayList<PlacesEntity> list);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int page);

        void loadfromNextPage();

        void getPlaces();

    }
}

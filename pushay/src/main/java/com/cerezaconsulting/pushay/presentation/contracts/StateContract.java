package com.cerezaconsulting.pushay.presentation.contracts;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.PlacesEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface StateContract {
    interface View extends BaseView<Presenter> {

        void getStates(ArrayList<PlacesEntity> list);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int page);

        void loadfromNextPage();

        void getPlaces();

    }
}

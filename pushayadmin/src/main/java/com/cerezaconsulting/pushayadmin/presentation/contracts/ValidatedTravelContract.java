package com.cerezaconsulting.pushayadmin.presentation.contracts;


import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface ValidatedTravelContract {
    interface View extends BaseView<Presenter> {

        void getListTravel(ArrayList<ReservationEntity> list);

        void clickItemTravel(ReservationEntity reservationEntity);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {


        void loadOrdersFromPage(int page);

        void loadFromNextPage();

        void getListTravel(int page);

    }
}

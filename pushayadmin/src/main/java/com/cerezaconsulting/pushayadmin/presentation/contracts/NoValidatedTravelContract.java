package com.cerezaconsulting.pushayadmin.presentation.contracts;

import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 17/05/17.
 */

public interface NoValidatedTravelContract {
    interface View extends BaseView<Presenter> {

        void getListTravel(ArrayList<ReservationEntity> list);

        void showDetailsTravel(ReservationEntity reservationEntity);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int id, int page);

        void loadFromNextPage(int id);

        void startLoad(int id);

        void loadListTravel(int id, final int page);


    }
}


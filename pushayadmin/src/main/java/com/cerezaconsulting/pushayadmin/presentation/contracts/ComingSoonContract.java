package com.cerezaconsulting.pushayadmin.presentation.contracts;

import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 17/05/17.
 */

public interface ComingSoonContract {
    interface View extends BaseView<Presenter> {

        void getComingSoonList(ArrayList<ReservationEntity> list);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int page);

        void loadfromNextPage();

        void loadList();
    }
}

package com.cerezaconsulting.pushay.presentation.contracts;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 31/05/17.
 */

public interface ListSchedulesContract {
    interface View extends BaseView<Presenter> {

        void getListGuideByDestiny(ArrayList<SchedulesEntity> list);


        void showDetailsTickets(SchedulesEntity schedulesEntity);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(String destinyName, String date,int page);

        void loadfromNextPage(String destinyName, String date);

        void startLoad(String destinyName, String date);

        void getListGuideByDestiny(String destinyName, String date, int page);



    }
}

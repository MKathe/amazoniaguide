package com.cerezaconsulting.pushay.presentation.contracts;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 31/05/17.
 */

public interface ListSchedulesContract {
    interface View extends BaseView<Presenter> {

        void getListSchedulesByDay(ArrayList<SchedulesEntity> list);

        void showDetailsTickets(SchedulesEntity schedulesEntity);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int page);

        void loadfromNextPage();

        void loadList(int id);


    }
}

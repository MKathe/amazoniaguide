package com.cerezaconsulting.pushayadmin.presentation.contracts;


import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface DestinyContract {
    interface View extends BaseView<Presenter> {

        void getDestiny(ArrayList<DestinyTravelEntity> list);

        void clickItemDestiny(DestinyTravelEntity destinyTravelEntity);

        void sendCreateSchedules(SchedulesEntity schedulesEntity);

        void createScheduleSuccesful(String msg);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int id, int page);

        void loadfromNextPage(int id);

        void getDestiny(int id, int page);

        void startLoad(int id);

        void createSchedules(SchedulesEntity schedulesEntity);

    }
}

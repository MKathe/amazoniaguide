package com.cerezaconsulting.pushayadmin.presentation.contracts;

import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/07/17.
 */

public interface CreateSchedulesContract {
    interface View extends BaseView<Presenter> {

        void sendCreateSchedules(SchedulesEntity schedulesEntity);

        void createScheduleSuccesful(String msg);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void createSchedules(SchedulesEntity schedulesEntity);

    }
}

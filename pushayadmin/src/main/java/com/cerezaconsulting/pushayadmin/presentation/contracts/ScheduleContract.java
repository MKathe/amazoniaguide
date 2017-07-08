package com.cerezaconsulting.pushayadmin.presentation.contracts;

import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 22/06/17.
 */

public interface ScheduleContract {
    interface View extends BaseView<Presenter> {

        void getSchedules(ArrayList<SchedulesEntity> list);
        void clickEditSchedules(SchedulesEntity schedulesEntity);
        void sendEditSchedules(SchedulesEntity schedulesEntity);
        void editSuccessful(String daySelected, String msg);

        void clickDeleteSchedules(SchedulesEntity schedulesEntity);
        void deleteSchedules(SchedulesEntity schedulesEntity);
        void deleteSuccessful(String daySelected,String msg);
        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int page);
        void loadFromNextPage();
        void loadList(String token, final int page);
        void edit(SchedulesEntity schedulesEntity);
        void delete(SchedulesEntity schedulesEntity);

    }
}

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
        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int page);
        void loadFromNextPage();
        void loadList(String token, final int page);

    }
}

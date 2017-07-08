package com.cerezaconsulting.pushay.presentation.contracts;


import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface DestinyContract {
    interface View extends BaseView<Presenter> {

        void getDestiny(ArrayList<DestinyTravelEntity> list);

        void clickItemDestiny(DestinyTravelEntity destinyTravelEntity);


        boolean isActive();
    }

    interface Presenter extends BasePresenter {


        void listDestiny(int id);

    }
}

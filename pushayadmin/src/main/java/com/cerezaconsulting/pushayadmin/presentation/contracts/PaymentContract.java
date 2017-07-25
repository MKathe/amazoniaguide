package com.cerezaconsulting.pushayadmin.presentation.contracts;


import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface PaymentContract {
    interface View extends BaseView<Presenter> {

        void getListPayment(ArrayList<ReservationEntity> list);

        void clickItemPayment(ReservationEntity reservationEntity);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {


        void loadOrdersFromPage(int id, int page);

        void loadFromNextPage(int id);

        void getListPaymentPendient(int page);

        void getListPaymentGoal(int page);

        void startLoad(int id);

    }
}

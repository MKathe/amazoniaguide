package com.cerezaconsulting.pushay.presentation.contracts;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.TicketEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 31/05/17.
 */

public interface TicketsContract {
    interface View extends BaseView<Presenter> {

        void getTickets(ArrayList<TicketEntity> list);

        void showDetailsTickets(TicketEntity reservationEntity);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int page);

        void loadfromNextPage();

        void loadList();


    }
}

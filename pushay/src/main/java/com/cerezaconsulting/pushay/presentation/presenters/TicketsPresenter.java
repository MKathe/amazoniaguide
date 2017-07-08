package com.cerezaconsulting.pushay.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushay.data.entities.ReservationEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.presentation.contracts.TicketsContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.TicketItem;

/**
 * Created by katherine on 31/05/17.
 */

public class TicketsPresenter implements TicketsContract.Presenter, TicketItem {

    private final TicketsContract.View mView;
    private final SessionManager mSessionManager;

    public TicketsPresenter(TicketsContract.View mView, Context context) {
        this.mView = mView;
        this.mSessionManager = new SessionManager(context);
        this.mView.setPresenter(this);

    }
    @Override
    public void clickItem(ReservationEntity ticketEntity) {
        mView.showDetailsTickets(ticketEntity);

    }

    @Override
    public void deleteItem(ReservationEntity ticketEntity, int position) {

    }

    @Override
    public void loadOrdersFromPage(int page) {

    }

    @Override
    public void loadfromNextPage() {

    }

    @Override
    public void loadList() {
    }

    @Override
    public void start() {

    }
}

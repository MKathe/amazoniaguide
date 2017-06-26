package com.cerezaconsulting.pushay.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushay.data.entities.CityEntity;
import com.cerezaconsulting.pushay.data.entities.CountryEntity;
import com.cerezaconsulting.pushay.data.entities.DayEntity;
import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushay.data.entities.PlacesEntity;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.data.entities.TicketEntity;
import com.cerezaconsulting.pushay.data.entities.UserEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.presentation.contracts.TicketsContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.TicketItem;

import java.util.ArrayList;

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
    public void clickItem(TicketEntity ticketEntity) {
        mView.showDetailsTickets(ticketEntity);

    }

    @Override
    public void deleteItem(TicketEntity ticketEntity, int position) {

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

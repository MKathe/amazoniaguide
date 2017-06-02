package com.cerezaconsulting.pushay.presentation.presenters.commons;

import com.cerezaconsulting.pushay.data.entities.PlacesEntity;
import com.cerezaconsulting.pushay.data.entities.TicketEntity;

/**
 * Created by katherine on 31/05/17.
 */

public interface TicketItem {
    void clickItem(TicketEntity ticketEntity);

    void deleteItem(TicketEntity ticketEntity, int position);
}

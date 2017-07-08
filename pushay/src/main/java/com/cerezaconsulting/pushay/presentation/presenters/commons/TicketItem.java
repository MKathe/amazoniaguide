package com.cerezaconsulting.pushay.presentation.presenters.commons;

import com.cerezaconsulting.pushay.data.entities.ReservationEntity;

/**
 * Created by katherine on 31/05/17.
 */

public interface TicketItem {
    void clickItem(ReservationEntity ticketEntity);

    void deleteItem(ReservationEntity ticketEntity, int position);
}

package com.cerezaconsulting.pushayadmin.presentation.presenters.commons;


import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface PlaceItem {

    void clickItem(ReservationEntity reservationEntity);

    void deleteItem(ReservationEntity reservationEntity, int position);
}

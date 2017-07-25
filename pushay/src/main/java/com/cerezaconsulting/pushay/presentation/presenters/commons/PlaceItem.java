package com.cerezaconsulting.pushay.presentation.presenters.commons;

import com.cerezaconsulting.pushay.data.entities.PlacesEntity;
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface PlaceItem {

    void clickItem(ReservationEntity reservationEntity);

    void deleteItem(ReservationEntity reservationEntity, int position);
}

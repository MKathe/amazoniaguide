package com.cerezaconsulting.pushay.presentation.presenters.commons;


import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface DestinyItem {

    void clickItem(DestinyTravelEntity destinyTravelEntity);

    void deleteItem(DestinyTravelEntity destinyTravelEntity, int position);
}

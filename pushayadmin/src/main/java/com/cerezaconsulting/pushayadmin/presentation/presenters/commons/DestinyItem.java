package com.cerezaconsulting.pushayadmin.presentation.presenters.commons;


import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface DestinyItem {

    void clickItem(DestinyTravelEntity destinyTravelEntity);

    void deleteItem(DestinyTravelEntity destinyTravelEntity, int position);
}

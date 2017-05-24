package com.cerezaconsulting.pushay.presentation.presenters.commons;

import com.cerezaconsulting.pushay.data.entities.PlacesEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface PlaceItem {

    void clickItem(PlacesEntity placesEntity);

    void deleteItem(PlacesEntity placesEntity, int position);
}

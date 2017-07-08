package com.cerezaconsulting.pushay.presentation.presenters.commons;


import com.cerezaconsulting.pushay.data.entities.CityEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface CitiesItem {

    void clickItem(CityEntity cityEntity);

    void deleteItem(CityEntity cityEntity, int position);
}
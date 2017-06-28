package com.cerezaconsulting.pushayadmin.presentation.presenters.commons;


import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface CountriesItem {

    void clickItem(CountryEntity countryEntity);

    void deleteItem(CountryEntity countryEntity, int position);
}

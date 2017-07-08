package com.cerezaconsulting.pushay.presentation.presenters.commons;


import com.cerezaconsulting.pushay.data.entities.CountryEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface CountriesItem {

    void clickItem(CountryEntity countryEntity);

    void deleteItem(CountryEntity countryEntity, int position);
}

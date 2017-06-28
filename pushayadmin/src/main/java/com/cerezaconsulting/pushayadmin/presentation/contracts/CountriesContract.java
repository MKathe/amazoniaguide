package com.cerezaconsulting.pushayadmin.presentation.contracts;


import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface CountriesContract {
    interface View extends BaseView<Presenter> {

        void getCountries(ArrayList<CountryEntity> list);

        void clickItemCountry(CountryEntity countryEntity);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int page);

        void loadfromNextPage();

        void getPlaces();

    }
}

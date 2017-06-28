package com.cerezaconsulting.pushayadmin.presentation.contracts;


import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface CitiesContract {
    interface View extends BaseView<Presenter> {

        void getCities(ArrayList<CityEntity> list);

        void clickItemCities(CityEntity cityEntity);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {


        void getCities(int id);

    }
}

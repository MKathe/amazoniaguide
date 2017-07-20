package com.cerezaconsulting.pushay.presentation.contracts;



import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.CityEntity;

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


        void loadOrdersFromPage(int id, int page);

        void loadfromNextPage(int id);

        void startLoad(int id);

        void getCities(int id, int page);

    }
}

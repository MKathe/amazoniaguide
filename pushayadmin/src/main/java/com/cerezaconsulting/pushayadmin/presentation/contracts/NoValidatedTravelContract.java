package com.cerezaconsulting.pushayadmin.presentation.contracts;

import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 17/05/17.
 */

public interface NoValidatedTravelContract {
    interface View extends BaseView<Presenter> {

        void getListTravel(ArrayList<ReservationEntity> list);

        void showDetailsTravel(ReservationEntity reservationEntity);

        void sendValidateTravelWithCode(String code, boolean is_confirm);

        void sendValidateTravelWithQr(int id, boolean is_confirm);

        void showDetailsValidate(String msg);

        boolean isActive();

//TGbtNVA9

    }

    interface Presenter extends BasePresenter {

        void loadOrdersFromPage(int id, int page);

        void loadFromNextPage(int id);

        void startLoad(int id);

        void loadListTravel(int id, final int page);

        void validatedTravelWithCode(String code, boolean is_confirm);

        void validatedTravelWithQr(int id, boolean is_confirm);




    }
}


package com.cerezaconsulting.pushay.presentation.contracts;


import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by katherine on 17/05/17.
 */

public interface CreateReservationContract {
    interface View extends BaseView<Presenter> {

        void createReservationResponse(String msg);

        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void createReservation(String token, int num_coupons, boolean is_confirm, String name);

    }
}


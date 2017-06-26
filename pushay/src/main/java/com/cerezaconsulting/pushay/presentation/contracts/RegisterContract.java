package com.cerezaconsulting.pushay.presentation.contracts;

import android.support.annotation.NonNull;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.UserEntity;

/**
 * Created by katherine on 3/05/17.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void registerSucessful(UserEntity userEntity);
        void errorRegister(String msg);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void registerUser(@NonNull UserEntity userEntity);

    }
}

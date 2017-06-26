package com.cerezaconsulting.pushayadmin.presentation.contracts;


import android.support.annotation.NonNull;

import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;

/**
 * Created by katherine on 3/05/17.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter> {

        void registerSuccessful(UserEntity userEntity);
        void errorRegister(String msg);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void registerUser(@NonNull UserEntity userEntity);

    }
}

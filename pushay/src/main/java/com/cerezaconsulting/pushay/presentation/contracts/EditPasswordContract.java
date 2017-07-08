package com.cerezaconsulting.pushay.presentation.contracts;

import android.support.annotation.NonNull;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;


/**
 * Created by junior on 01/06/16.
 */
public interface EditPasswordContract {

    interface View extends BaseView<Presenter> {

        void updatePassword();
        void showUpdateSucces();
        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void updatePassword(@NonNull String token,
                            @NonNull String old_password,
                            @NonNull String new_password,
                            @NonNull String email);


    }
}

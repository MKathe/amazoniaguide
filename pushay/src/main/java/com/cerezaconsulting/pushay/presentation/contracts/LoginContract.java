package com.cerezaconsulting.pushay.presentation.contracts;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushay.data.entities.UserEntity;

/**
 * Created by katherine on 3/05/17.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccessful(UserEntity userEntity);
        void errorLogin(String msg);
        void showDialogForgotPassword();
        void showSendEmail(String email);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loginUser(String username, String password);
        void getProfile(AccessTokenEntity token);
        void loginUserFacebook(String token);
        void openSession(AccessTokenEntity token, UserEntity userEntity);
        void sendEmail(String email);


    }
}

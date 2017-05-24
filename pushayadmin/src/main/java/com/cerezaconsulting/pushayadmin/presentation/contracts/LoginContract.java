package com.cerezaconsulting.pushayadmin.presentation.contracts;

import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;

/**
 * Created by katherine on 12/05/17.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSucessful();
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loginUser(String username, String password);

    }
}

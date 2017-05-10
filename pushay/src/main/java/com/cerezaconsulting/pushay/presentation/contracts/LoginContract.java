package com.cerezaconsulting.pushay.presentation.contracts;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;

/**
 * Created by katherine on 3/05/17.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSucessful();
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loginUser();

    }
}

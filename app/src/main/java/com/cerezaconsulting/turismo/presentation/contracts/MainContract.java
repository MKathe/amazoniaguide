package com.cerezaconsulting.turismo.presentation.contracts;

import com.cerezaconsulting.turismo.core.BasePresenter;
import com.cerezaconsulting.turismo.core.BaseView;

/**
 * Especifica el contrato entre la vista y el presentador para logueo
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {


        boolean isActive();
    }

    interface Presenter extends BasePresenter {


    }
}

package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.presentation.contracts.RegisterContract;

/**
 * Created by katherine on 3/05/17.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mView;
    private Context context;
    private SessionManager mSessionManager;


    public RegisterPresenter(RegisterContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        this.mSessionManager = new SessionManager(context);
    }


    @Override
    public void start() {

    }


    public void registerUser() {

    }


}

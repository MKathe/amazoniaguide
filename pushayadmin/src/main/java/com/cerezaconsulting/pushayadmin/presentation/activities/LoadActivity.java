package com.cerezaconsulting.pushayadmin.presentation.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;

/**
 * Created by katherine on 12/05/17.
 */

public class LoadActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        if (savedInstanceState == null)
            initialProcess();
    }

    private void initialProcess() {
        SessionManager mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.isLogin()){

            next(this,null, TravelActivity.class, true);
        }else{
            next(this,null, LandingActivity.class, true);
        }
    }
}

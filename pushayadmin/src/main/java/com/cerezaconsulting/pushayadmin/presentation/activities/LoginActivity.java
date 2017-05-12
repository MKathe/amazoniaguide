package com.cerezaconsulting.pushayadmin.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.presentation.fragments.LoginFragment;
import com.cerezaconsulting.pushayadmin.utils.ActivityUtils;

/**
 * Created by katherine on 12/05/17.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        LoginFragment fragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = LoginFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }

        // Create the presenter
       // new LoginPresenter(fragment,this);
    }
}

package com.cerezaconsulting.pushay.presentation.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.presentation.fragments.CitiesFragment;
import com.cerezaconsulting.pushay.presentation.fragments.CreateReservationFragment;
import com.cerezaconsulting.pushay.presentation.presenters.CitiesPresenter;
import com.cerezaconsulting.pushay.presentation.presenters.CreateReservationPresenter;
import com.cerezaconsulting.pushay.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 25/07/17.
 */

public class CreateReservationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);

        toolbar.setTitle("Elige la ciudad");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        CreateReservationFragment fragment = (CreateReservationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);
        if (fragment == null) {
            fragment = CreateReservationFragment.newInstance(getIntent().getExtras());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }
        new CreateReservationPresenter(fragment,this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

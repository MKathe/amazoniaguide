package com.cerezaconsulting.pushay.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.presentation.fragments.CitiesFragment;
import com.cerezaconsulting.pushay.presentation.fragments.ListSchedulesFragment;
import com.cerezaconsulting.pushay.presentation.presenters.CitiesPresenter;
import com.cerezaconsulting.pushay.presentation.presenters.ListSchedulesPresenter;
import com.cerezaconsulting.pushay.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 5/07/17.
 */

public class ListSchedulesActivity extends BaseActivity{
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);

        toolbar.setTitle("Elige a tu guía");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        ListSchedulesFragment fragment = (ListSchedulesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = ListSchedulesFragment.newInstance(getIntent().getExtras());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }
        new ListSchedulesPresenter(fragment,this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

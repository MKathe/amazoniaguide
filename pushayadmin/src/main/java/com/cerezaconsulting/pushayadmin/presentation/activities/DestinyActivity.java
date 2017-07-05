package com.cerezaconsulting.pushayadmin.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.presentation.fragments.CitiesFragment;
import com.cerezaconsulting.pushayadmin.presentation.fragments.DestinyFragment;
import com.cerezaconsulting.pushayadmin.presentation.presenters.CitiesPresenter;
import com.cerezaconsulting.pushayadmin.presentation.presenters.DestinyPresenter;
import com.cerezaconsulting.pushayadmin.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 3/07/17.
 */

public class DestinyActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);

        toolbar.setTitle("ELige la ciudad");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        DestinyFragment fragment = (DestinyFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = DestinyFragment.newInstance(getIntent().getExtras());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }
        new DestinyPresenter(fragment,this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
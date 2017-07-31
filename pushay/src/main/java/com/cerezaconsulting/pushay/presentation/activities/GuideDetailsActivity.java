package com.cerezaconsulting.pushay.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.presentation.fragments.GuideDetailsFragment;
import com.cerezaconsulting.pushay.presentation.fragments.ListSchedulesFragment;
import com.cerezaconsulting.pushay.presentation.presenters.ListSchedulesPresenter;
import com.cerezaconsulting.pushay.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 3/05/17.
 */

public class GuideDetailsActivity extends BaseActivity {
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

       GuideDetailsFragment fragment = (GuideDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = GuideDetailsFragment.newInstance(getIntent().getExtras());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }
        //new ListSchedulesPresenter(fragment,this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        SchedulesEntity schedulesEntity = (SchedulesEntity) getIntent().getExtras().getSerializable("schedulesEntity");
        DestinyTravelEntity destinyTravelEntity = schedulesEntity.getDestiny();
        Bundle bundle = new Bundle();
        bundle.putString("date", getIntent().getExtras().getString("date"));
        bundle.putSerializable("destinyTravelEntity", destinyTravelEntity );
        next(GuideDetailsActivity.this, bundle, ListSchedulesActivity.class, false);
        return true;
    }

}

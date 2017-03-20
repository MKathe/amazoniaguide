package com.cerezaconsulting.turismo.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;


import com.cerezaconsulting.turismo.R;
import com.cerezaconsulting.turismo.core.BaseActivity;
import com.cerezaconsulting.turismo.presentation.fragments.ExampleFragment;
import com.cerezaconsulting.turismo.presentation.presenters.ExamplePresenter;
import com.cerezaconsulting.turismo.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExampleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);

        toolbar.setTitle("Ejemplo");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        ExampleFragment fragment = (ExampleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = ExampleFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }

        // Create the presenter
        new ExamplePresenter(fragment,this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

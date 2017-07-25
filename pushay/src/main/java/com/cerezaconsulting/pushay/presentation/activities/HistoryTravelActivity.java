package com.cerezaconsulting.pushay.presentation.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.presentation.fragments.HistoryTravelFragment;
import com.cerezaconsulting.pushay.presentation.presenters.HistoryTravelPresenter;
import com.cerezaconsulting.pushay.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 28/06/17.
 */

public class HistoryTravelActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);

        toolbar.setTitle("Historial de viajes");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        /*FrameLayout frameLayout = (FrameLayout) findViewById(R.id.body);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.layout_tab, null, false);
        frameLayout.addView(activityView);
        viewPager = (ViewPager) activityView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) activityView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/

       HistoryTravelFragment fragment = (HistoryTravelFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = HistoryTravelFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }
        new HistoryTravelPresenter(fragment,this);
    }

  /*  private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ValidatedTravelFragment(), "VALIDADO");
        adapter.addFragment(new ComingSoonFragment(), "NO VALIDADO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}


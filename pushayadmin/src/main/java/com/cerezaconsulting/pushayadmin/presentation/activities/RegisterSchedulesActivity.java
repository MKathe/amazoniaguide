package com.cerezaconsulting.pushayadmin.presentation.activities;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.core.NonSwipableViewPager;
import com.cerezaconsulting.pushayadmin.presentation.fragments.CitiesFragment;
import com.cerezaconsulting.pushayadmin.presentation.fragments.CountriesFragment;
import com.cerezaconsulting.pushayadmin.presentation.fragments.LandingFragment;
import com.cerezaconsulting.pushayadmin.presentation.fragments.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 28/06/17.
 */

public class RegisterSchedulesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private NonSwipableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);

        toolbar.setTitle("Horarios");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.body);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.fragment_register_schedules, null,false);

        frameLayout.addView(activityView);
        viewPager = (NonSwipableViewPager) activityView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CountriesFragment.newInstance());
        adapter.addFragment(CitiesFragment.newInstance());
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

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btn_save:

                viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);

                /*AlertUtils.getInstance().showAlert(getContext(), "Guardar horarios del día "+getDaySelected(daySelected), "Recuerde que al guardar los horarios estos serán fijos, no podrá cambiarlos a menos que uses la opción de horarios especiales",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                mPresenter.saveScheduleFromDay(mHealthClinicEntitySelected.getId(),
                                        mScheduleDaySelected);
                            }
                        });*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

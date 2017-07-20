package com.cerezaconsulting.pushayadmin.presentation.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.presentation.fragments.ComingSoonFragment;
import com.cerezaconsulting.pushayadmin.presentation.fragments.TodayFragment;
import com.cerezaconsulting.pushayadmin.utils.CircleTransform;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class TravelActivity extends BaseActivity {
    DrawerLayout mDrawer;
    NavigationView navigationView;
    SessionManager mSessionManager;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public TextView tv_username;
    public TextView tv_mail;
    public ImageView profile_image;
    public UserEntity mUser;
   // private CountriesFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSessionManager = new SessionManager(this);
        /**
         *Setup the DrawerLayout and NavigationView
         */
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.layout_content_frame);
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.layout_tab, null, false);


        /**
         * Setup click events on the Navigation View Items.
         */
        frameLayout.addView(activityView);
        viewPager = (ViewPager) activityView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) activityView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupDrawerContent(navigationView);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                    /* host Activity */
                mDrawer,                    /* DrawerLayout object */
                toolbar,
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name   /*"close drawer" description for accessibility */
        );
        mDrawerToggle.syncState();
        mDrawer.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle.syncState();
        View header = navigationView.getHeaderView(0);

        tv_username = (TextView) header.findViewById(R.id.tv_fullnanme);
        profile_image = (ImageView) header.findViewById(R.id.imageView);
        tv_mail = (TextView) header.findViewById(R.id.tv_email);
        //  startService(new Intent(this, GeolocationService.class));
        EventBus.getDefault().register(this);
        initHeader();
   }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateProfile(UserEntity userEntity) {
        if(userEntity!=null){
            tv_username.setText(userEntity.getFullName());
            tv_mail.setText(userEntity.getEmail());
            Glide.with(this)
                    .load(mSessionManager.getUserEntity().getPicture())
                    .transform(new CircleTransform(this))
                    .into(profile_image);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TodayFragment(), "Hoy");
        adapter.addFragment(new ComingSoonFragment(), "Próximamente");
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
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(false);
                        menuItem.setCheckable(false);
                        switch (menuItem.getItemId()) {
                            case R.id.action_my_profile:
                                Intent intent = new Intent(TravelActivity.this , ProfileActivity.class);
                                startActivityForResult(intent, 7);
                                //next(TravelActivity.this, null, ProfileActivity.class, false);
                                break;
                            case R.id.action_programming:
                                Bundle bundle = new Bundle();
                                bundle.putString("daySelected", "Lunes");
                                next(TravelActivity.this,bundle, SchedulesActivity.class, false);
                                break;
                            case R.id.action_help:
                                next(TravelActivity.this,null, SlideActivity.class, false);
                              break;
                            case R.id.action_info:
                             /*  Intent intent_connect = new Intent(getBaseContext(), ProfileActivity.class);
                                startActivityForResult(intent_connect,200);*/
                              break;
                            case R.id.action_sign_off:
                                CloseSession();
                                break;
                            default:
                                break;
                        }
                        menuItem.setChecked(false);
                      //  mDrawer.closeDrawers();
                        return true;
                    }

                });
    }

    private void CloseSession(){
        mSessionManager.closeSession();
        newActivityClearPreview(this, null, LoadActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initHeader() {

        mUser = mSessionManager.getUserEntity();
        if (mUser != null) {

            tv_username.setText(mUser.getFullName());
            tv_username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next(TravelActivity.this, null, ProfileActivity.class, false);
                }
            });
            tv_mail.setText(mUser.getEmail());

            if (mUser.getPicture() != null) {
                    Glide.with(this)
                    .load(mSessionManager.getUserEntity().getPicture())
                    .transform(new CircleTransform(this))
                    .into(profile_image);

            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 200:
                    initHeader();
                    break;
                case 7:
                    Glide.with(this)
                            .load(mSessionManager.getUserEntity().getPicture())
                            .transform(new CircleTransform(this))
                            .into(profile_image);
                    break;


            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

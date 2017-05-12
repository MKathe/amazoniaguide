package com.cerezaconsulting.pushayadmin;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by katherine on 12/05/17.
 */

public class PushayAdminAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Gotham-Book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}

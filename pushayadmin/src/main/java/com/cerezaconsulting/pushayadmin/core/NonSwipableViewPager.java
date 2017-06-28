package com.cerezaconsulting.pushayadmin.core;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by TOTTUS on 24/04/2016.
 */
public class NonSwipableViewPager extends ViewPager {
    private boolean enable;

    public NonSwipableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enable=false;
    }

    public NonSwipableViewPager(Context context) {
        super(context);
        this.enable=false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(this.enable){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(this.enable){
            return super.onTouchEvent(ev);
        }
        return false;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

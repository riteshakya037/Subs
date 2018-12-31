package com.riteshakya.subs.views.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings({ "SameParameterValue", "ConstantConditions" }) public class NonScrollableViewPager extends ViewPager {

    private boolean isPagingEnabled = false;

    public NonScrollableViewPager(Context context) {
        super(context);
        init();
    }

    private void init() {
        setMyScroller();
    }

    public NonScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if (this.isPagingEnabled) return super.onTouchEvent(event);
        else return false;
    }

    @Override public boolean performClick() {
        return isPagingEnabled && super.performClick();
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isPagingEnabled) {
            return super.onInterceptTouchEvent(event);
        } else return false;
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        isPagingEnabled = pagingEnabled;
    }

    private void setMyScroller() {
        try {
            Class<ViewPager> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class MyScroller extends Scroller {

        MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }
}
package io.subs.android.views.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("SameParameterValue") public class NonScrollableViewPager extends ViewPager {

    private boolean isPagingEnabled = false;

    public NonScrollableViewPager(Context context) {
        super(context);
    }

    public NonScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    @Override public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, false);
    }

    @Override public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
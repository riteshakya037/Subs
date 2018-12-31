package com.riteshakya.subs.views.screens.main_screen;

import com.riteshakya.subs.mvp.BaseRxPresenter;
import com.riteshakya.subs.views.adapters.MainScreenPagerAdapter;

import com.riteshakya.subs.mvp.BaseRxPresenter;
import com.riteshakya.subs.views.adapters.MainScreenPagerAdapter;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class MainActivityFragmentPresenterImpl extends BaseRxPresenter implements
        MainActivityFragmentPresenter {
    private final MainScreenPagerAdapter mPagerAdapter;
    private MainActivityView mainActivityView;

    @Inject public MainActivityFragmentPresenterImpl(MainScreenPagerAdapter mPagerAdapter) {
        this.mPagerAdapter = mPagerAdapter;
    }

    @Override public void setView(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override public void initializeAdaptor() {
        this.mainActivityView.setAdapter(mPagerAdapter);
    }
}

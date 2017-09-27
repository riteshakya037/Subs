package io.subs.android.views.screens.main_screen;

import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.views.adapters.MainScreenPagerAdapter;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class MainActivityFragmentPresenterImpl extends BaseRxPresenter implements
        MainActivityFragmentPresenter {
    private MainScreenPagerAdapter mPagerAdapter;
    private MainActivityView mainActivityView;

    @Inject public MainActivityFragmentPresenterImpl(MainScreenPagerAdapter mPagerAdapter) {
        this.mPagerAdapter = mPagerAdapter;
    }

    @Override public void setView(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override public void initialize() {
    }

    @Override public void initializeAdaptor() {
        this.mainActivityView.setAdapter(mPagerAdapter);
    }
}

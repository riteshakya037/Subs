package io.subs.android.views.screens.main_screen.user_details;

import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.views.adapters.DetailPagerAdapter;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class UserSubscriptionDetailPresenterImpl extends BaseRxPresenter
        implements UserSubscriptionDetailPresenter {
    private UserSubscriptionDetailView subscriptionDetailView;
    private final DetailPagerAdapter detailPagerAdapter;

    @Inject public UserSubscriptionDetailPresenterImpl(DetailPagerAdapter detailPagerAdapter) {
        this.detailPagerAdapter = detailPagerAdapter;
    }

    @Override public void setView(UserSubscriptionDetailView mainActivityView) {
        this.subscriptionDetailView = mainActivityView;
    }

    @Override public void initializeAdaptor() {
        this.subscriptionDetailView.setAdapter(detailPagerAdapter);
    }
}

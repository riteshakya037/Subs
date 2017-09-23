package io.subs.android.views.screens.add_subscription;

import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.views.adapters.AddSubscriptionPagerAdapter;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class AddSubscriptionPresenterImpl extends BaseRxPresenter
        implements AddSubscriptionPresenter {
    private AddSubscriptionPagerAdapter mPagerAdapter;
    private AddSubscriptionView addSubscriptionView;

    @Inject public AddSubscriptionPresenterImpl(AddSubscriptionPagerAdapter mPagerAdapter) {
        this.mPagerAdapter = mPagerAdapter;
    }

    @Override public void setView(AddSubscriptionView addSubscriptionView) {
        this.addSubscriptionView = addSubscriptionView;
    }

    @Override public void initialize() {

    }

    @Override public void initializeAdaptor() {
        this.addSubscriptionView.setAdapter(mPagerAdapter);
    }
}

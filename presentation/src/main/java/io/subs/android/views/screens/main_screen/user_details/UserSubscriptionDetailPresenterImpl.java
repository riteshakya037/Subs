package io.subs.android.views.screens.main_screen.user_details;

import io.subs.android.mvp.BaseRxPresenter;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class UserSubscriptionDetailPresenterImpl extends BaseRxPresenter
        implements UserSubscriptionDetailPresenter {
    private UserSubscriptionDetailView mainActivityView;

    @Inject public UserSubscriptionDetailPresenterImpl() {
    }

    @Override public void setView(UserSubscriptionDetailView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override public void initialize() {
    }
}

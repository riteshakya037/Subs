package io.subs.android.views.screens.main_screen.user_profile;

import io.subs.android.mvp.BaseRxPresenter;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */
public class UserProfileFragmentPresenterImpl extends BaseRxPresenter
        implements UserProfileFragmentPresenter {
    private UserProfileView mainActivityView;

    @Inject public UserProfileFragmentPresenterImpl() {
    }

    @Override public void setView(UserProfileView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override public void initialize() {
    }
}
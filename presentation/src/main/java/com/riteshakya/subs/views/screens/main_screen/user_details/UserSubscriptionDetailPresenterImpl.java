package com.riteshakya.subs.views.screens.main_screen.user_details;

import com.riteshakya.domain.models.UserProfile;
import com.riteshakya.domain.usecases.session.GetUserProfile;
import com.riteshakya.subs.mvp.BaseRxPresenter;
import com.riteshakya.subs.views.adapters.DetailPagerAdapter;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * @author Ritesh Shakya
 */

public class UserSubscriptionDetailPresenterImpl extends BaseRxPresenter
        implements UserSubscriptionDetailPresenter {
    private final DetailPagerAdapter detailPagerAdapter;
    private UserSubscriptionDetailView subscriptionDetailView;
    private GetUserProfile getUserProfile;

    @Inject
    public UserSubscriptionDetailPresenterImpl(DetailPagerAdapter detailPagerAdapter, GetUserProfile getUserProfile) {
        this.detailPagerAdapter = detailPagerAdapter;
        this.getUserProfile = getUserProfile;
    }

    @Override
    public void setView(UserSubscriptionDetailView mainActivityView) {
        this.subscriptionDetailView = mainActivityView;
    }

    @Override
    public void initialize() {
        getUserProfile();
    }

    @Override
    public void initializeAdaptor() {
        this.subscriptionDetailView.setAdapter(detailPagerAdapter);
    }

    private void getUserProfile() {
        manage(getUserProfile.execute(new DisposableObserver<UserProfile>() {
            @Override
            public void onNext(@NonNull UserProfile userProfile) {
                subscriptionDetailView.setName(userProfile.getUserFullName());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, null));
    }
}

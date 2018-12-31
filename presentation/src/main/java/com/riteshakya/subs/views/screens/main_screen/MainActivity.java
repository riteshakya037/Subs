package com.riteshakya.subs.views.screens.main_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.DaggerUserSubscriptionComponent;
import com.riteshakya.subs.di.components.UserSubscriptionComponent;
import com.riteshakya.subs.di.modules.UserSubscriptionModule;
import com.riteshakya.subs.views.base.DaggerBaseActivity;
import com.riteshakya.subs.views.screens.main_screen.user_details.UserSubscriptionDetailPresenter;
import com.riteshakya.subs.views.screens.main_screen.user_profile.UserProfileFragmentPresenter;
import com.riteshakya.domain.models.UserSubscription;

public class MainActivity extends DaggerBaseActivity<UserSubscriptionComponent>
        implements MainActivityFragmentPresenter.MainActivityFlowListener,
        UserProfileFragmentPresenter.UserProfileFlowListener,
        UserSubscriptionDetailPresenter.UserSubscriptionDetailFlowListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override protected int getContextView() {
        return R.layout.activity_fragment_container;
    }

    @Override protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new MainActivityFragment());
        }
    }

    @Override protected UserSubscriptionComponent getInjector() {
        return DaggerUserSubscriptionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userSubscriptionModule(new UserSubscriptionModule(this))
                .build();
    }

    @Override public void createSubscription(UserSubscription subscription) {
        navigator.navigateToUpdateSubscription(this, subscription);
    }

    @Override public void openAddSubscription() {
        navigator.navigateToSubscriptionList(this);
    }

    @Override public void openSettings() {
        navigator.navigateToSettingActivity(this);
    }
}

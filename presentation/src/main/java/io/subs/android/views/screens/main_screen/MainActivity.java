package io.subs.android.views.screens.main_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.subs.android.R;
import io.subs.android.di.components.DaggerUserSubscriptionComponent;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.di.modules.UserSubscriptionModule;
import io.subs.android.views.base.DaggerBaseActivity;
import io.subs.android.views.screens.main_screen.user_details.UserSubscriptionDetailPresenter;
import io.subs.android.views.screens.main_screen.user_profile.UserProfileFragmentPresenter;
import io.subs.domain.models.UserSubscription;

public class MainActivity extends DaggerBaseActivity<UserSubscriptionComponent>
        implements MainActivityFragmentPresenter.MainActivityFlowListener,
        UserProfileFragmentPresenter.UserProfileFlowListener,
        UserSubscriptionDetailPresenter.UserSubscriptionDetailFlowListener {
    private static final String TAG = "MainActivity";

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

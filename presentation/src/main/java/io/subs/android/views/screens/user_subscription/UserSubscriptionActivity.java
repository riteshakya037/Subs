package io.subs.android.views.screens.user_subscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.subs.android.R;
import io.subs.android.di.components.DaggerUserSubscriptionComponent;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.views.base.DaggerBaseActivity;

public class UserSubscriptionActivity extends DaggerBaseActivity<UserSubscriptionComponent> {
    private static final String TAG = "UserSubscriptionActivity";

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UserSubscriptionActivity.class);
    }

    @Override protected int getContextView() {
        return R.layout.activity_fragment_container;
    }

    @Override protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new UserSubscriptionListFragment());
        }
    }

    @Override protected UserSubscriptionComponent getInjector() {
        return DaggerUserSubscriptionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}

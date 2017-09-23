package io.subs.android.views.screens.add_subscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.subs.android.R;
import io.subs.android.di.components.DaggerSubscriptionComponent;
import io.subs.android.di.components.SubscriptionComponent;
import io.subs.android.views.base.DaggerBaseActivity;
import io.subs.domain.models.Subscription;

public class AddSubscriptionActivity extends DaggerBaseActivity<SubscriptionComponent>
        implements SubscriptionListFragment.SubscriptionListListener {
    private static final String TAG = "UserSubscriptionActivity";

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AddSubscriptionActivity.class);
    }

    public void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new AddSubscriptionFragment());
        }
    }

    @Override protected int getContextView() {
        return R.layout.activity_fragment_container;
    }

    @Override protected SubscriptionComponent getInjector() {
        return DaggerSubscriptionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override public void onSubscriptionClicked(Subscription subscription) {
        navigator.navigateToCreateSubscription(this, subscription);
    }

    @Override public void onCustomSubscriberCreate() {
        navigator.navigateToCreateSubscription(this, new Subscription());
    }
}

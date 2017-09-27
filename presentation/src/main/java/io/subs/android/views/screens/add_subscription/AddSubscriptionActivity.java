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
    private static final String TAG = "MainActivity";
    private static final int CREATE_SUBSCRIPTION = 100;

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
        navigator.navigateToUpdateSubscription(this, subscription, CREATE_SUBSCRIPTION);
    }

    @Override public void onCustomSubscriberCreate() {
        navigator.navigateToUpdateSubscription(this, new Subscription(), CREATE_SUBSCRIPTION);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_SUBSCRIPTION && resultCode == RESULT_OK) {
            finish();
        }
    }
}

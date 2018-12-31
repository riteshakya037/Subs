package com.riteshakya.subs.views.screens.add_subscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.riteshakya.domain.DatabaseNames;
import com.riteshakya.domain.models.Subscription;
import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.DaggerSubscriptionComponent;
import com.riteshakya.subs.di.components.SubscriptionComponent;
import com.riteshakya.subs.views.base.DaggerBaseActivity;

public class AddSubscriptionActivity extends DaggerBaseActivity<SubscriptionComponent>
        implements SubscriptionListFragment.SubscriptionListListener {
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
        navigator.navigateToUpdateSubscription(this,
                new Subscription(DatabaseNames.PATH_DEFAULT_IMAGE, Integer.toHexString(
                        ContextCompat.getColor(getApplicationComponent().context(),
                                R.color.colorDefault))), CREATE_SUBSCRIPTION);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_SUBSCRIPTION && resultCode == RESULT_OK) {
            finish();
        }
    }
}

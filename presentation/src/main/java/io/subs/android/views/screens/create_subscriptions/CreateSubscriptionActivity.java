package io.subs.android.views.screens.create_subscriptions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.subs.android.R;
import io.subs.android.di.components.DaggerUserSubscriptionComponent;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.views.base.DaggerBaseActivity;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.UserSubscription;
import org.parceler.ParcelClass;
import org.parceler.Parcels;

/**
 * @author Ritesh Shakya
 */
@ParcelClass(UserSubscription.class) public class CreateSubscriptionActivity
        extends DaggerBaseActivity<UserSubscriptionComponent> {
    private static final String INTENT_EXTRA_PARAM_SUBSCRIPTION = "subscription_id";
    private static final String INSTANCE_STATE_PARAM_SUBSCRIPTION = "state_subscription_id";

    private UserSubscription userSubscription;

    public static Intent getCallingIntent(Context context, Subscription subscription) {
        Intent callingIntent = new Intent(context, CreateSubscriptionActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_SUBSCRIPTION,
                Parcels.wrap(new UserSubscription(subscription)));
        return callingIntent;
    }

    @Override protected int getContextView() {
        return R.layout.activity_fragment_container;
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(INSTANCE_STATE_PARAM_SUBSCRIPTION,
                    Parcels.wrap(userSubscription));
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    public void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.userSubscription =
                    Parcels.unwrap(getIntent().getParcelableExtra(INTENT_EXTRA_PARAM_SUBSCRIPTION));
            addFragment(R.id.fragmentContainer,
                    CreateSubscriptionFragment.forSubscription(userSubscription));
        } else {
            this.userSubscription = Parcels.unwrap(
                    savedInstanceState.getParcelable(INSTANCE_STATE_PARAM_SUBSCRIPTION));
        }
    }

    @Override protected UserSubscriptionComponent getInjector() {
        return DaggerUserSubscriptionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}

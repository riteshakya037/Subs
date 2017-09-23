package io.subs.android.views.screens.create_subscriptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.fernandocejas.arrow.checks.Preconditions;
import io.subs.android.R;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import io.subs.domain.models.UserSubscription;
import org.parceler.Parcels;

/**
 * @author Ritesh Shakya
 */
public class CreateSubscriptionFragment extends BaseFragment
        implements CreateSubscriptionPresenter.CreateSubscriptionView {
    private static final String ARGS_USER_SUBSCRIPTION = "user_subscription";
    //@Inject CreateSubscriptionPresenter createSubscriptionPresenter;

    public static Fragment forSubscription(UserSubscription userSubscription) {
        CreateSubscriptionFragment createSubscriptionFragment = new CreateSubscriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_USER_SUBSCRIPTION, Parcels.wrap(userSubscription));
        createSubscriptionFragment.setArguments(bundle);
        return createSubscriptionFragment;
    }

    @Override protected int getLayout() {
        return R.layout.fragment_create_subscription;
    }

    @Override protected void initializeViews() {
        //this.createSubscriptionPresenter.setView(this);
        System.out.println("currentLoadType() = " + currentLoadType());
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    private UserSubscription currentLoadType() {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
        return Parcels.unwrap(arguments.getParcelable(ARGS_USER_SUBSCRIPTION));
    }
}

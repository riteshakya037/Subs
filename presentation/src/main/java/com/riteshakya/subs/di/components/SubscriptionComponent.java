package com.riteshakya.subs.di.components;

import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.di.modules.ActivityModule;
import com.riteshakya.subs.di.modules.SubscriptionModule;
import com.riteshakya.subs.views.screens.add_subscription.AddSubscriptionFragment;
import com.riteshakya.subs.views.screens.add_subscription.SubscriptionListFragment;

import dagger.Component;
import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.di.modules.ActivityModule;
import com.riteshakya.subs.di.modules.SubscriptionModule;
import com.riteshakya.subs.views.screens.add_subscription.AddSubscriptionFragment;
import com.riteshakya.subs.views.screens.add_subscription.SubscriptionListFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, SubscriptionModule.class
}) public interface SubscriptionComponent extends ActivityComponent {
    void inject(SubscriptionListFragment subscriptionListFragment);

    void inject(AddSubscriptionFragment addSubscriptionFragment);
}

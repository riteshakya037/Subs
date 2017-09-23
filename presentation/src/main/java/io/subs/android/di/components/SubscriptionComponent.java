package io.subs.android.di.components;

import dagger.Component;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.ActivityModule;
import io.subs.android.di.modules.SubscriptionModule;
import io.subs.android.views.screens.add_subscription.AddSubscriptionFragment;
import io.subs.android.views.screens.add_subscription.SubscriptionListFragment;

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

package io.subs.android.di.components;

import dagger.Component;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.ActivityModule;
import io.subs.android.di.modules.UserSubscriptionModule;
import io.subs.android.views.screens.create_subscriptions.CreateSubscriptionFragment;
import io.subs.android.views.screens.user_subscription.UserSubscriptionListFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, UserSubscriptionModule.class
}) public interface UserSubscriptionComponent extends ActivityComponent {
    void inject(UserSubscriptionListFragment userSubscriptionListFragment);

    void inject(CreateSubscriptionFragment createSubscriptionFragment);
}

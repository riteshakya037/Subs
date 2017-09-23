package io.subs.android.di.modules;

import dagger.Module;
import dagger.Provides;
import io.subs.android.di.PerActivity;
import io.subs.android.views.screens.add_subscription.AddSubscriptionPresenter;
import io.subs.android.views.screens.add_subscription.AddSubscriptionPresenterImpl;
import io.subs.android.views.screens.add_subscription.SubscriptionListPresenter;
import io.subs.android.views.screens.add_subscription.SubscriptionListPresenterImpl;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class SubscriptionModule {

    public SubscriptionModule() {
    }

    @Provides @PerActivity SubscriptionListPresenter provideSubscriptionListPresenter(
            SubscriptionListPresenterImpl subscriptionListPresenter) {
        return subscriptionListPresenter;
    }

    @Provides @PerActivity AddSubscriptionPresenter provideAddSubscriptionPresenter(
            AddSubscriptionPresenterImpl addSubscriptionPresenter) {
        return addSubscriptionPresenter;
    }
}

package com.riteshakya.subs.di.modules;

import com.riteshakya.subs.views.screens.add_subscription.SubscriptionListPresenter;
import com.riteshakya.subs.views.screens.add_subscription.SubscriptionListPresenterImpl;

import dagger.Module;
import dagger.Provides;
import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.views.screens.add_subscription.AddSubscriptionPresenter;
import com.riteshakya.subs.views.screens.add_subscription.AddSubscriptionPresenterImpl;
import com.riteshakya.subs.views.screens.add_subscription.SubscriptionListPresenter;
import com.riteshakya.subs.views.screens.add_subscription.SubscriptionListPresenterImpl;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class SubscriptionModule {

    public SubscriptionModule() {
    }

    @Provides
    SubscriptionListPresenter provideSubscriptionListPresenter(
            SubscriptionListPresenterImpl subscriptionListPresenter) {
        return subscriptionListPresenter;
    }

    @Provides @PerActivity AddSubscriptionPresenter provideAddSubscriptionPresenter(
            AddSubscriptionPresenterImpl addSubscriptionPresenter) {
        return addSubscriptionPresenter;
    }
}

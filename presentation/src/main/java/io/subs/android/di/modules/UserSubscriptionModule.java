package io.subs.android.di.modules;

import dagger.Module;
import dagger.Provides;
import io.subs.android.di.PerActivity;
import io.subs.android.views.screens.user_subscription.UserSubscriptionListPresenter;
import io.subs.android.views.screens.user_subscription.UserSubscriptionListPresenterImpl;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class UserSubscriptionModule {

    public UserSubscriptionModule() {
    }

    @Provides @PerActivity UserSubscriptionListPresenter provideUserSubscriptionListPresenter(
            UserSubscriptionListPresenterImpl subscriptionListPresenter) {
        return subscriptionListPresenter;
    }
}

package io.subs.android.di.modules.repository_modules;

import dagger.Module;
import dagger.Provides;
import io.subs.data.repository.UserSubscriptionDataRepository;
import io.subs.data.repository.datasource.user_subscriptions.FirebaseUserSubscriptionDataStore;
import io.subs.data.repository.datasource.user_subscriptions.UserSubscriptionDataStore;
import io.subs.domain.repository.IUserSubscriptionRepository;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module public class UserSubscriptionRepoModule {

    @Provides @Singleton IUserSubscriptionRepository provideUserSubscriptionRepository(
            UserSubscriptionDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides @Singleton UserSubscriptionDataStore provideUserSubscriptionDataStore(
            FirebaseUserSubscriptionDataStore subscriptionDataStore) {
        return subscriptionDataStore;
    }
}

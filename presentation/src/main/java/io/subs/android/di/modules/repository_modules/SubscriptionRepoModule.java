package io.subs.android.di.modules.repository_modules;

import dagger.Module;
import dagger.Provides;
import io.subs.data.repository.SubscriptionDataRepository;
import io.subs.data.repository.datasource.subscriptions.FirebaseSubscriptionDataStore;
import io.subs.data.repository.datasource.subscriptions.ISubscriptionDataStore;
import io.subs.domain.repository.ISubscriptionRepository;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module public class SubscriptionRepoModule {

    @Provides @Singleton ISubscriptionRepository provideSubscriptionRepository(
            SubscriptionDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides @Singleton ISubscriptionDataStore provideSubscriptionDataStore(
            FirebaseSubscriptionDataStore subscriptionDataStore) {
        return subscriptionDataStore;
    }
}

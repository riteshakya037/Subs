package com.riteshakya.subs.di.modules.repository_modules;

import dagger.Module;
import dagger.Provides;
import com.riteshakya.data.repository.SubscriptionDataRepository;
import com.riteshakya.data.repository.datasource.subscriptions.FirebaseSubscriptionDataStore;
import com.riteshakya.data.repository.datasource.subscriptions.ISubscriptionDataStore;
import com.riteshakya.domain.repository.ISubscriptionRepository;
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

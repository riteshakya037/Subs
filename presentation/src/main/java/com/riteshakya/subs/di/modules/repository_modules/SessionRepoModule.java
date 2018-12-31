package com.riteshakya.subs.di.modules.repository_modules;

import dagger.Module;
import dagger.Provides;
import com.riteshakya.data.repository.SessionDataRepository;
import com.riteshakya.data.repository.datasource.sessions.FirebaseSessionDataStore;
import com.riteshakya.data.repository.datasource.sessions.ISessionDataStore;
import com.riteshakya.domain.repository.ISessionRepository;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module public class SessionRepoModule {

    @Provides @Singleton ISessionRepository provideSessionRepository(
            SessionDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides @Singleton ISessionDataStore provideSessionDataStore(
            FirebaseSessionDataStore subscriptionDataStore) {
        return subscriptionDataStore;
    }
}

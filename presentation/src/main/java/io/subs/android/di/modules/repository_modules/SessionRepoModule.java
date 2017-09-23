package io.subs.android.di.modules.repository_modules;

import dagger.Module;
import dagger.Provides;
import io.subs.data.repository.SessionDataRepository;
import io.subs.data.repository.datasource.sessions.FirebaseSessionDataStore;
import io.subs.data.repository.datasource.sessions.ISessionDataStore;
import io.subs.domain.repository.ISessionRepository;
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

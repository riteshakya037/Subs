package io.subs.android.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.subs.android.SubsApplication;
import io.subs.android.UIThread;
import io.subs.android.imageloader.GlideLoader;
import io.subs.android.imageloader.IImageLoader;
import io.subs.data.executor.JobExecutor;
import io.subs.data.repository.SubscriptionDataRepository;
import io.subs.data.repository.datasource.FirebaseSubscriptionDataStore;
import io.subs.data.repository.datasource.SubscriptionDataStore;
import io.subs.domain.executor.PostExecutionThread;
import io.subs.domain.executor.ThreadExecutor;
import io.subs.domain.repository.SubscriptionRepository;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module public class ApplicationModule {
    private final SubsApplication application;

    public ApplicationModule(SubsApplication application) {
        this.application = application;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton SubscriptionRepository provideUserRepository(
            SubscriptionDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides @Singleton SubscriptionDataStore provideSubscriptionDataStore(
            FirebaseSubscriptionDataStore subscriptionDataStore) {
        return subscriptionDataStore;
    }

    @Singleton @Provides IImageLoader provideImageLoader() {
        return new GlideLoader(application);
    }
}

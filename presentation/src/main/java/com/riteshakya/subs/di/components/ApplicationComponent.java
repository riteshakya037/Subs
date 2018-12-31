package com.riteshakya.subs.di.components;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.repository.ISessionRepository;
import com.riteshakya.domain.repository.ISubscriptionRepository;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.subs.di.modules.ApplicationModule;
import com.riteshakya.subs.di.modules.repository_modules.SessionRepoModule;
import com.riteshakya.subs.di.modules.repository_modules.SubscriptionRepoModule;
import com.riteshakya.subs.di.modules.repository_modules.UserSubscriptionRepoModule;
import com.riteshakya.subs.imageloader.IImageLoader;
import com.riteshakya.subs.views.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application.
@Component(modules = {
        ApplicationModule.class, SubscriptionRepoModule.class, UserSubscriptionRepoModule.class,
        SessionRepoModule.class
})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    IThreadExecutor threadExecutor();

    IPostExecutionThread postExecutionThread();

    ISubscriptionRepository subscriptionRepository();

    IUserSubscriptionRepository userSubscriptionRepository();

    ISessionRepository sessionRepository();

    GoogleApiClient googleApiClient();

    IImageLoader imageLoader();
}

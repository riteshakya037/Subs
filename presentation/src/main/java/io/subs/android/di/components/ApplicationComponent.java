package io.subs.android.di.components;

import android.content.Context;
import dagger.Component;
import io.subs.android.di.modules.ApplicationModule;
import io.subs.android.di.modules.repository_modules.SessionRepoModule;
import io.subs.android.di.modules.repository_modules.SubscriptionRepoModule;
import io.subs.android.di.modules.repository_modules.UserSubscriptionRepoModule;
import io.subs.android.imageloader.IImageLoader;
import io.subs.android.views.base.BaseActivity;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.repository.ISessionRepository;
import io.subs.domain.repository.ISubscriptionRepository;
import io.subs.domain.repository.IUserSubscriptionRepository;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {
        ApplicationModule.class, SubscriptionRepoModule.class, UserSubscriptionRepoModule.class,
        SessionRepoModule.class
}) public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    IThreadExecutor threadExecutor();

    IPostExecutionThread postExecutionThread();

    ISubscriptionRepository subscriptionRepository();

    IUserSubscriptionRepository userSubscriptionRepository();

    ISessionRepository sessionRepository();

    IImageLoader imageLoader();
}

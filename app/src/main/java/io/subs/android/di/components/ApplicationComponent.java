package io.subs.android.di.components;

import android.content.Context;
import dagger.Component;
import io.subs.android.di.modules.ApplicationModule;
import io.subs.android.imageloader.IImageLoader;
import io.subs.android.views.BaseActivity;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.repository.ISubscriptionRepository;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class) public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    IThreadExecutor threadExecutor();

    IPostExecutionThread postExecutionThread();

    ISubscriptionRepository subscriptionRepository();

    IImageLoader imageLoader();
}

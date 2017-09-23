package io.subs.android.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.subs.android.SubsApplication;
import io.subs.android.UIThread;
import io.subs.android.imageloader.GlideLoader;
import io.subs.android.imageloader.IImageLoader;
import io.subs.data.executor.JobExecutor;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
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

    @Provides @Singleton IThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton IPostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Singleton @Provides IImageLoader provideImageLoader() {
        return new GlideLoader(application);
    }
}

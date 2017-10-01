package io.subs.android.di.modules;

import android.content.Context;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import dagger.Module;
import dagger.Provides;
import io.subs.android.R;
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

    @Provides @Singleton GoogleApiClient provideGoogleApiClient() {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                        provideApplicationContext().getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
        return new GoogleApiClient.Builder(provideApplicationContext()).addApi(
                Auth.GOOGLE_SIGN_IN_API, gso).build();
    }

    @Provides @Singleton IPostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Singleton @Provides IImageLoader provideImageLoader() {
        return new GlideLoader(application);
    }
}

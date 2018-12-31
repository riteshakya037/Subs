package com.riteshakya.subs.di.modules;

import android.content.Context;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.riteshakya.subs.SubsApplication;
import com.riteshakya.subs.UIThread;

import dagger.Module;
import dagger.Provides;
import com.riteshakya.subs.R;
import com.riteshakya.subs.SubsApplication;
import com.riteshakya.subs.UIThread;
import com.riteshakya.subs.imageloader.GlideLoader;
import com.riteshakya.subs.imageloader.IImageLoader;
import com.riteshakya.data.executor.JobExecutor;
import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module public class ApplicationModule {
    private final SubsApplication application;

    public ApplicationModule(SubsApplication application) {
        this.application = application;
    }

    @SuppressWarnings("WeakerAccess") @Provides @Singleton Context provideApplicationContext() {
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

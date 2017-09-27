package io.subs.android.di.modules;

import dagger.Module;
import dagger.Provides;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.base.InjectableModule;
import io.subs.android.views.screens.splash.SplashScreenActivity;
import io.subs.android.views.screens.splash.SplashScreenPresenter;
import io.subs.android.views.screens.splash.SplashScreenPresenterImpl;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class SessionModule extends InjectableModule<SplashScreenActivity> {

    public SessionModule() {
    }

    public SessionModule(SplashScreenActivity activity) {
        super(activity);
    }

    @Provides @PerActivity SplashScreenPresenter.SplashScreenFlowListener providesFlowListener() {
        return getBoundClass();
    }

    @Provides @PerActivity SplashScreenPresenter provideSplashScreenPresenter(
            SplashScreenPresenterImpl splashScreenPresenter) {
        return splashScreenPresenter;
    }

    @Provides @PerActivity SplashScreenPresenter.SplashScreenView providesView() {
        return getBoundClass();
    }
}

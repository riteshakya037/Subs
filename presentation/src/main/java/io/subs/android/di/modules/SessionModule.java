package io.subs.android.di.modules;

import dagger.Module;
import dagger.Provides;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.base.InjectableModule;
import io.subs.android.views.base.BaseActivity;
import io.subs.android.views.screens.login.LoginActivity;
import io.subs.android.views.screens.login.LoginPresenter;
import io.subs.android.views.screens.login.LoginPresenterImpl;
import io.subs.android.views.screens.settings.SettingActivity;
import io.subs.android.views.screens.settings.SettingPresenter;
import io.subs.android.views.screens.settings.SettingPresenterImpl;
import io.subs.android.views.screens.splash.SplashScreenActivity;
import io.subs.android.views.screens.splash.SplashScreenPresenter;
import io.subs.android.views.screens.splash.SplashScreenPresenterImpl;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class SessionModule extends InjectableModule<BaseActivity> {

    public SessionModule() {
    }

    public SessionModule(SplashScreenActivity activity) {
        super(activity);
    }

    public SessionModule(LoginActivity loginActivity) {
        super(loginActivity);
    }

    public SessionModule(SettingActivity settingActivity) {
        super(settingActivity);
    }

    // // TODO: 0028, September 28, 2017 better implementation
    @Provides @PerActivity SplashScreenPresenter.SplashScreenFlowListener providesFlowListener() {
        return (SplashScreenPresenter.SplashScreenFlowListener) getBoundClass();
    }

    @Provides @PerActivity SplashScreenPresenter provideSplashScreenPresenter(
            SplashScreenPresenterImpl splashScreenPresenter) {
        return splashScreenPresenter;
    }

    @Provides @PerActivity LoginPresenter.LoginFlowListener providesLoginFlowListener() {
        return (LoginPresenter.LoginFlowListener) getBoundClass();
    }

    @Provides @PerActivity LoginPresenter providesLoginPresenter(
            LoginPresenterImpl loginPresenter) {
        return loginPresenter;
    }

    @Provides @PerActivity SettingPresenter providesSettingPresenter(
            SettingPresenterImpl settingPresenter) {
        return settingPresenter;
    }

    @Provides @PerActivity SettingPresenter.SettingFlowListener providesSettingFlowListener() {
        return (SettingPresenter.SettingFlowListener) getBoundClass();
    }
}

package com.riteshakya.subs.di.modules;

import dagger.Module;
import dagger.Provides;
import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.di.modules.base.InjectableModule;
import com.riteshakya.subs.views.base.BaseActivity;
import com.riteshakya.subs.views.screens.login.LoginActivity;
import com.riteshakya.subs.views.screens.login.LoginPresenter;
import com.riteshakya.subs.views.screens.login.LoginPresenterImpl;
import com.riteshakya.subs.views.screens.settings.SettingActivity;
import com.riteshakya.subs.views.screens.settings.SettingPresenter;
import com.riteshakya.subs.views.screens.settings.SettingPresenterImpl;
import com.riteshakya.subs.views.screens.splash.SplashScreenActivity;
import com.riteshakya.subs.views.screens.splash.SplashScreenPresenter;
import com.riteshakya.subs.views.screens.splash.SplashScreenPresenterImpl;

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

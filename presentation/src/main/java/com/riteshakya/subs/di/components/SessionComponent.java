package com.riteshakya.subs.di.components;

import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.di.modules.ActivityModule;
import com.riteshakya.subs.di.modules.SessionModule;

import dagger.Component;
import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.di.modules.ActivityModule;
import com.riteshakya.subs.di.modules.SessionModule;
import com.riteshakya.subs.views.screens.login.LoginFragment;
import com.riteshakya.subs.views.screens.settings.SettingFragment;
import com.riteshakya.subs.views.screens.splash.SplashScreenFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, SessionModule.class
}) public interface SessionComponent extends ActivityComponent {
    void inject(SplashScreenFragment splashScreenFragment);

    void inject(LoginFragment loginFragment);

    void inject(SettingFragment settingFragment);
}

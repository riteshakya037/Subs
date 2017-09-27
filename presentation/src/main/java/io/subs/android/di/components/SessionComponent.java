package io.subs.android.di.components;

import dagger.Component;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.ActivityModule;
import io.subs.android.di.modules.SessionModule;
import io.subs.android.views.screens.splash.SplashScreenFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, SessionModule.class
}) public interface SessionComponent extends ActivityComponent {
    void inject(SplashScreenFragment splashScreenFragment);
}

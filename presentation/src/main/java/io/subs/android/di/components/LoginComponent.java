package io.subs.android.di.components;

import dagger.Component;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.ActivityModule;
import io.subs.android.di.modules.LoginModule;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, LoginModule.class
}) public interface LoginComponent extends ActivityComponent {
}

package io.subs.android.di.components;

import android.app.Activity;
import dagger.Component;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.ActivityModule;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link PerActivity}
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {
    //Exposed to sub-graphs.
    @SuppressWarnings("unused") Activity activity();
}

package io.subs.android.di.modules;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import io.subs.android.di.PerActivity;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module public class ActivityModule {
    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides @PerActivity Activity activity() {
        return this.activity;
    }

    @Provides @PerActivity FragmentManager provideFragmentManager() {
        return this.activity.getSupportFragmentManager();
    }
}

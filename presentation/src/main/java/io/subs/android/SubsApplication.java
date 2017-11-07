package io.subs.android;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import io.subs.android.di.components.ApplicationComponent;
import io.subs.android.di.components.DaggerApplicationComponent;
import io.subs.android.di.modules.ApplicationModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * @author Ritesh Shakya
 */

public class SubsApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Fabric.with(this, new Crashlytics());
        this.initializeInjector();
        this.initializeCalligraphy();
        this.initializeLeakDetection();
    }

    private void initializeCalligraphy() {
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder().setDefaultFontPath("fonts/ProductSansRegular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}

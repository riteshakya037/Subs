package com.riteshakya.subs.views.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.riteshakya.subs.SubsApplication;
import com.riteshakya.subs.di.components.ApplicationComponent;
import com.riteshakya.subs.di.modules.ActivityModule;
import com.riteshakya.subs.navigation.Navigator;

import butterknife.ButterKnife;
import com.riteshakya.subs.SubsApplication;
import com.riteshakya.subs.di.components.ApplicationComponent;
import com.riteshakya.subs.di.modules.ActivityModule;
import com.riteshakya.subs.navigation.Navigator;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject protected Navigator navigator;

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContextView());
        ButterKnife.bind(this);
    }

    @SuppressWarnings("SameReturnValue") protected abstract int getContextView();

    protected abstract void initializeActivity(Bundle savedInstanceState);

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    @SuppressWarnings("SameParameterValue") protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction =
                this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((SubsApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}

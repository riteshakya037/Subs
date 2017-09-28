package io.subs.android.views.screens.splash;

import android.os.Bundle;
import io.subs.android.R;
import io.subs.android.di.components.DaggerSessionComponent;
import io.subs.android.di.components.SessionComponent;
import io.subs.android.di.modules.SessionModule;
import io.subs.android.views.base.DaggerBaseActivity;

public class SplashScreenActivity extends DaggerBaseActivity<SessionComponent>
        implements SplashScreenPresenter.SplashScreenFlowListener {

    @Override protected int getContextView() {
        return R.layout.activity_fragment_container;
    }

    @Override protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new SplashScreenFragment());
        }
    }

    @Override protected SessionComponent getInjector() {
        return DaggerSessionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .sessionModule(new SessionModule(this))
                .build();
    }

    @Override public void openLoginScreen() {
        navigator.navigateToLoginScreen(this);
        finish();
    }

    @Override public void openMainScreen() {
        navigator.navigateToMainScreen(this);
        finish();
    }
}

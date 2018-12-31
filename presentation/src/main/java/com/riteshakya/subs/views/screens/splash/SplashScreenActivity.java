package com.riteshakya.subs.views.screens.splash;

import android.os.Bundle;

import com.riteshakya.subs.di.components.SessionComponent;
import com.riteshakya.subs.di.modules.SessionModule;
import com.riteshakya.subs.views.base.DaggerBaseActivity;

import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.DaggerSessionComponent;
import com.riteshakya.subs.di.components.SessionComponent;
import com.riteshakya.subs.di.modules.SessionModule;
import com.riteshakya.subs.views.base.DaggerBaseActivity;

//import io.subs.android.di.components.DaggerSessionComponent;

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
        return
                DaggerSessionComponent.builder()
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

package com.riteshakya.subs.views.screens.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.riteshakya.subs.di.components.SessionComponent;
import com.riteshakya.subs.views.base.BaseFragment;

import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.SessionComponent;
import com.riteshakya.subs.views.base.BaseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SplashScreenFragment extends BaseFragment
        implements SplashScreenPresenter.SplashScreenView {
    @SuppressWarnings("WeakerAccess") @Inject SplashScreenPresenter splashScreenPresenter;

    @Override protected int getLayout() {
        return R.layout.fragment_splash_screen;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        if (splashScreenPresenter != null) {
            splashScreenPresenter.setView(this);
            splashScreenPresenter.initialize();
        }
    }

    @Override protected void injectDagger() {
        getComponent(SessionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(splashScreenPresenter);
    }

    @Override public void showError(String message) {

    }
}

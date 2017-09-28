package io.subs.android.views.screens.splash;

import android.os.Bundle;
import android.view.View;
import io.subs.android.R;
import io.subs.android.di.components.SessionComponent;
import io.subs.android.views.base.BaseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SplashScreenFragment extends BaseFragment
        implements SplashScreenPresenter.SplashScreenView {
    @Inject SplashScreenPresenter splashScreenPresenter;

    @Override protected int getLayout() {
        return R.layout.fragment_splash_screen;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        this.splashScreenPresenter.setView(this);
        splashScreenPresenter.initialize();
    }

    @Override protected void injectDagger() {
        getComponent(SessionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(splashScreenPresenter);
    }

    @Override public void showError(String message) {

    }
}

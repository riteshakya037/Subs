package io.subs.android.views.screens.splash;

import io.subs.android.mvp.FlowListener;
import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface SplashScreenPresenter extends IPresenter {
    void initialize();

    void setView(SplashScreenView splashScreenView);

    interface SplashScreenView extends IView {

        void showError(String message);
    }

    interface SplashScreenFlowListener extends FlowListener {
        void openLoginScreen();

        void openMainScreen();
    }
}

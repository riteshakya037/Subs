package com.riteshakya.subs.views.screens.splash;

import com.riteshakya.subs.mvp.FlowListener;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

import com.riteshakya.subs.mvp.FlowListener;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface SplashScreenPresenter extends IPresenter {
    void initialize();

    void setView(SplashScreenView splashScreenView);

    interface SplashScreenView extends IView {

        @SuppressWarnings({ "EmptyMethod", "unused" }) void showError(String message);
    }

    interface SplashScreenFlowListener extends FlowListener {
        void openLoginScreen();

        void openMainScreen();
    }
}

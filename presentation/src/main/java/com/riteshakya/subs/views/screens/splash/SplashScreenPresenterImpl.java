package com.riteshakya.subs.views.screens.splash;

import android.os.Handler;

import com.riteshakya.subs.mvp.BaseRxPresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import com.riteshakya.subs.mvp.BaseRxPresenter;
import com.riteshakya.domain.usecases.session.GetLoginStatus;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SplashScreenPresenterImpl extends BaseRxPresenter implements SplashScreenPresenter {

    private final SplashScreenFlowListener splashScreenFlowListener;
    private SplashScreenView splashScreenView;
    private final GetLoginStatus getLoginStatus;

    @Inject public SplashScreenPresenterImpl(
            SplashScreenPresenter.SplashScreenFlowListener splashScreenFlowListener,
            GetLoginStatus getLoginStatus) {

        this.splashScreenFlowListener = splashScreenFlowListener;
        this.getLoginStatus = getLoginStatus;
    }

    @Override public void setView(SplashScreenView splashScreenView) {
        this.splashScreenView = splashScreenView;
    }

    @Override public void initialize() {
        final Handler finalHandler = new Handler();
        this.manage(
                getLoginStatus.execute(new DisposableObserver<GetLoginStatus.LoginStatusType>() {
                    @Override public void onNext(
                            @NonNull final GetLoginStatus.LoginStatusType loginStatusType) {
                        Runnable runnable = () -> {
                            if (loginStatusType == GetLoginStatus.LoginStatusType.ACTIVE) {
                                splashScreenFlowListener.openMainScreen();
                            } else {
                                splashScreenFlowListener.openLoginScreen();
                            }
                        };
                        finalHandler.postDelayed(runnable, 2000);
                    }

                    @Override public void onError(@NonNull Throwable e) {
                        splashScreenView.showError(e.getMessage());
                    }

                    @Override public void onComplete() {

                    }
                }, null));
    }
}

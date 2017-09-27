package io.subs.android.views.screens.splash;

import android.os.Handler;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.domain.usecases.session.GetLoginStatus;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SplashScreenPresenterImpl extends BaseRxPresenter implements SplashScreenPresenter {

    private final SplashScreenFlowListener splashScreenFlowListener;
    private final SplashScreenView splashScreenView;
    private GetLoginStatus getLoginStatus;

    @Inject public SplashScreenPresenterImpl(
            SplashScreenPresenter.SplashScreenFlowListener splashScreenFlowListener,
            SplashScreenPresenter.SplashScreenView splashScreenView,
            GetLoginStatus getLoginStatus) {

        this.splashScreenFlowListener = splashScreenFlowListener;
        this.splashScreenView = splashScreenView;
        this.getLoginStatus = getLoginStatus;
    }

    @Override public void initialize() {
        final Handler finalHandler = new Handler();
        this.manage(
                getLoginStatus.execute(new DisposableObserver<GetLoginStatus.LoginStatusType>() {
                    @Override public void onNext(
                            @NonNull final GetLoginStatus.LoginStatusType loginStatusType) {
                        Runnable runnable = new Runnable() {
                            public void run() {
                                if (loginStatusType == GetLoginStatus.LoginStatusType.ACTIVE) {
                                    splashScreenFlowListener.openMainScreen();
                                } else {
                                    splashScreenFlowListener.openLoginScreen();
                                }
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

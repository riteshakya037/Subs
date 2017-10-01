package io.subs.android.views.screens.settings;

import android.content.Intent;
import io.subs.android.mvp.FlowListener;
import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface SettingPresenter extends IPresenter {

    void setView(SettingView settingView);

    void initialize();

    void signOutUser();

    void shareApplication();

    void sendFeedback();

    void rateApplication();

    interface SettingView extends IView {

    }

    interface SettingFlowListener extends FlowListener {
        void showLoginScreen();

        void startActivity(Intent goToMarket);
    }
}

package com.riteshakya.subs.views.screens.settings;

import android.content.Intent;

import com.riteshakya.subs.mvp.FlowListener;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

import com.riteshakya.subs.mvp.FlowListener;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface SettingPresenter extends IPresenter {

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

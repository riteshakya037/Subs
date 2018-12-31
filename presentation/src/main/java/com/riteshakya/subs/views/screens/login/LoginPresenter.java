package com.riteshakya.subs.views.screens.login;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.riteshakya.subs.mvp.FlowListener;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

import com.riteshakya.subs.mvp.FlowListener;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface LoginPresenter extends IPresenter {

    void setView(LoginView loginView);

    void initialize();

    void validateResult(Intent data);

    GoogleApiClient getGoogleApiClient();

    interface LoginFlowListener extends FlowListener {

        void openMainActivity();
    }

    interface LoginView extends IView {
        FragmentActivity getActivity();
    }
}

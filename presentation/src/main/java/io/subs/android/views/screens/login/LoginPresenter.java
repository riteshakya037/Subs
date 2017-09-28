package io.subs.android.views.screens.login;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import io.subs.android.mvp.FlowListener;
import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface LoginPresenter extends IPresenter {

    void initializeSession();

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

package io.subs.android.views.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import butterknife.OnClick;
import com.google.android.gms.auth.api.Auth;
import io.subs.android.R;
import io.subs.android.di.components.SessionComponent;
import io.subs.android.views.base.BaseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class LoginFragment extends BaseFragment implements LoginPresenter.LoginView {
    private static final int RC_SIGN_IN = 0;
    @SuppressWarnings("WeakerAccess") @Inject LoginPresenter loginPresenter;

    @Override protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        if (loginPresenter != null) {
            loginPresenter.setView(this);
            loginPresenter.initialize();
        }
    }

    @OnClick(R.id.activity_login_google) void login() {
        Intent signInIntent =
                Auth.GoogleSignInApi.getSignInIntent(loginPresenter.getGoogleApiClient());
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override protected void injectDagger() {
        getComponent(SessionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(loginPresenter);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            loginPresenter.validateResult(data);
        }
    }
}

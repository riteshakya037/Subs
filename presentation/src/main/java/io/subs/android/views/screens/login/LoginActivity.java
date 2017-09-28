package io.subs.android.views.screens.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import io.subs.android.R;
import io.subs.android.di.components.DaggerSessionComponent;
import io.subs.android.di.components.SessionComponent;
import io.subs.android.di.modules.SessionModule;
import io.subs.android.views.base.DaggerBaseActivity;
import io.subs.android.views.screens.main_screen.MainActivityFragment;

public class LoginActivity extends DaggerBaseActivity<SessionComponent>
        implements LoginPresenter.LoginFlowListener {
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static Intent getCallingIntent(Activity context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected int getContextView() {
        return R.layout.activity_fragment_container;
    }

    @Override protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new LoginFragment());
        }
    }

    @Override protected SessionComponent getInjector() {
        return DaggerSessionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .sessionModule(new SessionModule(this))
                .build();
    }

    @Override public void openMainActivity() {
        navigator.navigateToMainScreen(this);
    }
}

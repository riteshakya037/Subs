package com.riteshakya.subs.views.screens.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.riteshakya.subs.di.components.SessionComponent;
import com.riteshakya.subs.di.modules.SessionModule;
import com.riteshakya.subs.views.base.DaggerBaseActivity;

import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.DaggerSessionComponent;
import com.riteshakya.subs.di.components.SessionComponent;
import com.riteshakya.subs.di.modules.SessionModule;
import com.riteshakya.subs.views.base.DaggerBaseActivity;

public class LoginActivity extends DaggerBaseActivity<SessionComponent>
        implements LoginPresenter.LoginFlowListener {

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
        finishAffinity();
    }
}

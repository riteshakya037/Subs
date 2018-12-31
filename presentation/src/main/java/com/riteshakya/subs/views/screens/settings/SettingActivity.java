package com.riteshakya.subs.views.screens.settings;

import android.content.Context;
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

public class SettingActivity extends DaggerBaseActivity<SessionComponent>
        implements SettingPresenter.SettingFlowListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override protected int getContextView() {
        return R.layout.activity_fragment_container;
    }

    @Override protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, SettingFragment.createInstance());
        }
    }

    @Override protected SessionComponent getInjector() {
        return DaggerSessionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .sessionModule(new SessionModule(this))
                .build();
    }

    @Override public void showLoginScreen() {
        navigator.navigateToLoginScreen(this);
        finish();
    }
}

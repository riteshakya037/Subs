package io.subs.android.views.screens.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.subs.android.R;
import io.subs.android.di.components.DaggerSessionComponent;
import io.subs.android.di.components.SessionComponent;
import io.subs.android.di.modules.SessionModule;
import io.subs.android.views.base.DaggerBaseActivity;

public class SettingActivity extends DaggerBaseActivity<SessionComponent>
        implements SettingPresenter.SettingFlowListener {
    private static final String TAG = "SettingActivity";

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override protected int getContextView() {
        return R.layout.activity_fragment_container;
    }

    @Override protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new SettingFragment());
        }
    }

    @Override protected SessionComponent getInjector() {
        return DaggerSessionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .sessionModule(new SessionModule(this))
                .build();
    }
}

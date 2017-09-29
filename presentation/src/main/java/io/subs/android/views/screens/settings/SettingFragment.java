package io.subs.android.views.screens.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import io.subs.android.R;
import io.subs.android.di.components.SessionComponent;
import io.subs.android.views.base.BaseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SettingFragment extends BaseFragment implements SettingPresenter.SettingView {
    @Inject SettingPresenter settingPresenter;

    public static Fragment createInstance() {
        return new SettingFragment();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_setting;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        this.settingPresenter.setView(this);
        if (savedInstanceState == null) {
        }
    }

    @Override protected void injectDagger() {
        getComponent(SessionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(settingPresenter);
    }
}

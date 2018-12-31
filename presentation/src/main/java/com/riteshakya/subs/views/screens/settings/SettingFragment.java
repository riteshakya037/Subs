package com.riteshakya.subs.views.screens.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.riteshakya.subs.di.components.SessionComponent;
import com.riteshakya.subs.views.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import com.riteshakya.subs.BuildConfig;
import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.SessionComponent;
import com.riteshakya.subs.views.base.BaseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") public class SettingFragment extends BaseFragment
        implements SettingPresenter.SettingView {
    @Inject SettingPresenter settingPresenter;
    @BindView(R.id.fragment_setting_version) TextView tvAppVersion;
    @Inject Context context;

    public static Fragment createInstance() {
        return new SettingFragment();
    }

    @OnClick(R.id.fragment_setting_sign_out) void signOut() {
        settingPresenter.signOutUser();
    }

    @OnClick(R.id.fragment_setting_rate) void rateApplication() {
        settingPresenter.rateApplication();
    }

    @OnClick(R.id.fragment_setting_share) void shareApplication() {
        settingPresenter.shareApplication();
    }

    @OnClick(R.id.fragment_setting_feedback) void sendFeedback() {
        settingPresenter.sendFeedback();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_setting;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setAppVersion();
        }
    }

    @Override protected void injectDagger() {
        getComponent(SessionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(settingPresenter);
    }

    private void setAppVersion() {
        tvAppVersion.setText(context.getString(R.string.version_string, BuildConfig.VERSION_NAME));
    }
}

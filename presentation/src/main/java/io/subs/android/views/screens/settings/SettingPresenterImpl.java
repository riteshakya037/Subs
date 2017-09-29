package io.subs.android.views.screens.settings;

import io.subs.android.mvp.BaseRxPresenter;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SettingPresenterImpl extends BaseRxPresenter
        implements SettingPresenter {
    private SettingView mainActivityView;

    @Inject public SettingPresenterImpl() {
    }

    @Override public void setView(SettingView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override public void initialize() {
    }
}

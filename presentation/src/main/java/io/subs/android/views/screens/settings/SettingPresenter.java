package io.subs.android.views.screens.settings;

import io.subs.android.mvp.FlowListener;
import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface SettingPresenter extends IPresenter {

    void setView(SettingView settingView);

    void initialize();

    interface SettingView extends IView {

    }

    interface SettingFlowListener extends FlowListener {
    }
}

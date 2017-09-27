package io.subs.android.views.screens.main_screen.user_profile;

import io.subs.android.mvp.FlowListener;
import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

/**
 * @author Ritesh Shakya
 */
public interface UserProfileFragmentPresenter extends IPresenter {

    void setView(UserProfileView userProfileView);

    void initialize();

    interface UserProfileView extends IView {

    }

    interface UserProfileFlowListener extends FlowListener {
    }
}
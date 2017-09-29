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

    void openSettings();

    interface UserProfileView extends IView {

        void setName(String userFullName);

        void setEmail(String userEmail);

        void setMaxSubs(int currentSubs, int subAvailable);
    }

    interface UserProfileFlowListener extends FlowListener {
        void openSettings();
    }
}
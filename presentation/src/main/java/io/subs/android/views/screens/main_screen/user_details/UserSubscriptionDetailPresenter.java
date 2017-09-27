package io.subs.android.views.screens.main_screen.user_details;

import io.subs.android.mvp.FlowListener;
import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface UserSubscriptionDetailPresenter extends IPresenter {

    void setView(UserSubscriptionDetailView userSubscriptionDetailView);

    void initialize();

    interface UserSubscriptionDetailView extends IView {

    }

    interface UserSubscriptionDetailFlowListener extends FlowListener {
    }
}

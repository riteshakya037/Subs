package io.subs.android.views.screens.main_screen.user_details;

import android.support.v4.view.PagerAdapter;
import io.subs.android.mvp.FlowListener;
import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface UserSubscriptionDetailPresenter extends IPresenter {

    void setView(UserSubscriptionDetailView userSubscriptionDetailView);

    void initializeAdaptor();

    interface UserSubscriptionDetailView extends IView {

        void setAdapter(PagerAdapter pagerAdapter);
    }

    interface UserSubscriptionDetailFlowListener extends FlowListener {
    }
}

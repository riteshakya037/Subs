package com.riteshakya.subs.views.screens.main_screen.user_details;

import android.support.v4.view.PagerAdapter;
import com.riteshakya.subs.mvp.FlowListener;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

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

package com.riteshakya.subs.views.screens.main_screen;

import android.support.v4.view.PagerAdapter;
import com.riteshakya.subs.mvp.FlowListener;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;
import com.riteshakya.domain.models.UserSubscription;

/**
 * @author Ritesh Shakya
 */

public interface MainActivityFragmentPresenter extends IPresenter {

    void setView(MainActivityView mainActivityView);

    void initializeAdaptor();

    interface MainActivityView extends IView {

        void setAdapter(PagerAdapter addSubscriptionAdaptor);
    }

    interface MainActivityFlowListener extends FlowListener {
        void createSubscription(UserSubscription subscription);

        void openAddSubscription();
    }
}

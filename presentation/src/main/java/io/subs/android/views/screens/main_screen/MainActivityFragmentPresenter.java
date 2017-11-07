package io.subs.android.views.screens.main_screen;

import android.support.v4.view.PagerAdapter;
import io.subs.android.mvp.FlowListener;
import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;
import io.subs.domain.models.UserSubscription;

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

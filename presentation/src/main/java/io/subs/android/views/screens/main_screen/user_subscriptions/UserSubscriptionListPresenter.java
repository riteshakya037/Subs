package io.subs.android.views.screens.main_screen.user_subscriptions;

import android.support.v7.widget.RecyclerView;
import io.subs.android.mvp.IPresenter;
import io.subs.android.views.base.LoadDataView;

/**
 * @author Ritesh Shakya
 */

public interface UserSubscriptionListPresenter extends IPresenter {

    void setView(UserSubscriptionListView userSubscriptionListView);

    void initialize();

    void initializeAdaptor();

    void openAddSubscription();

    interface UserSubscriptionListView extends LoadDataView {

        void setAdapter(RecyclerView.Adapter addSubscriptionAdaptor);

        void isAddEnabled(boolean isValid);
    }
}

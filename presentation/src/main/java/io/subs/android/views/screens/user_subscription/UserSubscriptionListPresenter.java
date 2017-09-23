package io.subs.android.views.screens.user_subscription;

import android.support.v7.widget.RecyclerView;
import io.subs.android.mvp.IPresenter;
import io.subs.android.views.base.LoadDataView;
import io.subs.domain.models.UserSubscription;

/**
 * @author Ritesh Shakya
 */

public interface UserSubscriptionListPresenter extends IPresenter {

    void setView(UserSubscriptionListView userSubscriptionListView);

    void initialize();

    void initializeAdaptor();

    interface UserSubscriptionListView extends LoadDataView {
        void createSubscription(UserSubscription subscription);

        void setAdapter(RecyclerView.Adapter addSubscriptionAdaptor);
    }
}

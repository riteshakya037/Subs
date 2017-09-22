package io.subs.android.views.screens.add_subscription;

import android.support.v7.widget.RecyclerView;
import io.subs.android.mvp.IPresenter;
import io.subs.android.views.base.LoadDataView;
import io.subs.domain.models.Subscription;

/**
 * @author Ritesh Shakya
 */

public interface SubscriptionListPresenter extends IPresenter {

    void setView(SubscriptionListView subscriptionListView);

    void initialize();

    void initializeAdaptor();

    interface SubscriptionListView extends LoadDataView {
        /**
         * View a {@link Subscription} profile/details.
         *
         * @param subscription The user that will be shown.
         */
        void createSubscription(Subscription subscription);

        void setAdapter(RecyclerView.Adapter addSubscriptionAdaptor);
    }
}

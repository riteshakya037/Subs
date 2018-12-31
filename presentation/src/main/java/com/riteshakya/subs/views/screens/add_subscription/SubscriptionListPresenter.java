package com.riteshakya.subs.views.screens.add_subscription;

import android.support.v7.widget.RecyclerView;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.views.base.LoadDataView;
import com.riteshakya.domain.models.Subscription;
import com.riteshakya.domain.models.enums.SubscriptionType;

/**
 * @author Ritesh Shakya
 */

public interface SubscriptionListPresenter extends IPresenter {

    void setView(SubscriptionListView subscriptionListView);

    void initialize(SubscriptionType subscriptionType);

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

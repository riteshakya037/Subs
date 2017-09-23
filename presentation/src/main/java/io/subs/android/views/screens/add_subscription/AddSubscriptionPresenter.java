package io.subs.android.views.screens.add_subscription;

import android.support.v4.view.PagerAdapter;
import io.subs.android.mvp.IPresenter;
import io.subs.domain.models.Subscription;

/**
 * @author Ritesh Shakya
 */

public interface AddSubscriptionPresenter extends IPresenter {

    void setView(AddSubscriptionView addSubscriptionView);

    void initialize();

    void initializeAdaptor();

    interface AddSubscriptionView {
        /**
         * View a {@link Subscription} profile/details.
         *
         * @param subscription The user that will be shown.
         */
        void createSubscription(Subscription subscription);

        void setAdapter(PagerAdapter addSubscriptionAdaptor);
    }
}

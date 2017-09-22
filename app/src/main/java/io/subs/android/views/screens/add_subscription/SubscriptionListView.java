package io.subs.android.views.screens.add_subscription;

import io.subs.android.views.base.LoadDataView;
import io.subs.domain.models.Subscription;

/**
 * @author Ritesh Shakya
 */

interface SubscriptionListView extends LoadDataView {
    /**
     * Render a user list in the UI.
     *
     * @param subscriptions The collection of {@link Subscription} that will be shown.
     */
    void renderSubscriptions(Subscription subscriptions);

    /**
     * View a {@link Subscription} profile/details.
     *
     * @param subscription The user that will be shown.
     */
    void createSubscription(Subscription subscription);
}

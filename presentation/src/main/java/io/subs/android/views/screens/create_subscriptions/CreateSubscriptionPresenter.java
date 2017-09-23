package io.subs.android.views.screens.create_subscriptions;

import io.subs.android.mvp.IPresenter;

/**
 * @author Ritesh Shakya
 */

public interface CreateSubscriptionPresenter extends IPresenter {
    void setView(CreateSubscriptionView createSubscriptionView);

    void initialize();

    interface CreateSubscriptionView {
    }
}

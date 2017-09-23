package io.subs.android.views.screens.create_subscriptions;

import io.subs.android.mvp.BaseRxPresenter;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class CreateSubscriptionPresenterImpl extends BaseRxPresenter
        implements CreateSubscriptionPresenter {

    private CreateSubscriptionView createSubscriptionView;

    @Inject public CreateSubscriptionPresenterImpl() {
    }

    @Override public void setView(CreateSubscriptionView createSubscriptionView) {

        this.createSubscriptionView = createSubscriptionView;
    }

    @Override public void initialize() {

    }
}

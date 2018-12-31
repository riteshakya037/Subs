package com.riteshakya.subs.views.screens.add_subscription;

import android.support.v4.view.PagerAdapter;
import com.riteshakya.subs.mvp.IPresenter;

/**
 * @author Ritesh Shakya
 */

public interface AddSubscriptionPresenter extends IPresenter {

    void setView(AddSubscriptionView addSubscriptionView);

    void initialize();

    void initializeAdaptor();

    interface AddSubscriptionView {

        void setAdapter(PagerAdapter addSubscriptionAdaptor);
    }
}

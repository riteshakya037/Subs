package io.subs.android.views.screens.main_screen.user_details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import io.subs.android.R;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class UserSubscriptionDetailFragment extends BaseFragment
        implements UserSubscriptionDetailPresenter.UserSubscriptionDetailView {
    @Inject UserSubscriptionDetailPresenter userSubscriptionDetailPresenter;

    public static Fragment createInstance() {
        return new UserSubscriptionDetailFragment();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_user_subscription_detail;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        this.userSubscriptionDetailPresenter.setView(this);
        if (savedInstanceState == null) {
        }
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(userSubscriptionDetailPresenter);
    }
}

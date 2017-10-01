package io.subs.android.views.screens.main_screen.user_details;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
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
    @BindView(R.id.fragment_user_subs_tabs) TabLayout mTabLayout;
    @BindView(R.id.fragment_user_subs_pager) ViewPager mViewPager;

    public static Fragment createInstance() {
        return new UserSubscriptionDetailFragment();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_user_subscription_detail;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        if (userSubscriptionDetailPresenter != null) {
            userSubscriptionDetailPresenter.setView(this);
        }
        if (savedInstanceState == null) {
            setupViewPager();
        }
    }

    private void setupViewPager() {
        mTabLayout.setupWithViewPager(mViewPager);
        userSubscriptionDetailPresenter.initializeAdaptor();
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(userSubscriptionDetailPresenter);
    }

    @Override public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter != null) {
            mViewPager.setAdapter(pagerAdapter);
        }
    }
}

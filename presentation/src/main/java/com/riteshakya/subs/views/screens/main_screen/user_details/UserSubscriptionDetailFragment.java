package com.riteshakya.subs.views.screens.main_screen.user_details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.UserSubscriptionComponent;
import com.riteshakya.subs.views.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") public class UserSubscriptionDetailFragment extends BaseFragment
        implements UserSubscriptionDetailPresenter.UserSubscriptionDetailView {
    @Inject UserSubscriptionDetailPresenter userSubscriptionDetailPresenter;
    @BindView(R.id.fragment_user_subs_tabs) TabLayout mTabLayout;
    @BindView(R.id.fragment_user_subs_pager) ViewPager mViewPager;
    @BindView(R.id.welcomeText)
    TextView welcomeText;

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
            userSubscriptionDetailPresenter.initialize();
        }
    }

    private void setupViewPager() {
        mTabLayout.setupWithViewPager(mViewPager);
        userSubscriptionDetailPresenter.initializeAdaptor();
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(userSubscriptionDetailPresenter);
    }

    @Override
    public void setName(String userFullName) {
        welcomeText.setText("Hey " + getFirstName(userFullName) + "!");
    }

    public String getFirstName(String fullname) {
        String firstName;
        if (fullname.split("[^\\w']+").length > 1) {
            firstName = fullname.split("[^\\w']+")[0];
        } else {
            firstName = fullname;
        }
        return firstName;
    }

    @Override public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter != null) {
            mViewPager.setAdapter(pagerAdapter);
        }
    }
}

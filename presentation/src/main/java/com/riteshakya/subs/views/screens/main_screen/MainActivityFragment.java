package com.riteshakya.subs.views.screens.main_screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.riteshakya.subs.di.components.UserSubscriptionComponent;
import com.riteshakya.subs.views.base.BaseFragment;
import com.riteshakya.subs.views.component.NonScrollableViewPager;

import butterknife.BindView;
import devlight.io.library.ntb.NavigationTabBar;
import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.UserSubscriptionComponent;
import com.riteshakya.subs.views.base.BaseFragment;
import com.riteshakya.subs.views.component.NonScrollableViewPager;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") public class MainActivityFragment extends BaseFragment
        implements MainActivityFragmentPresenter.MainActivityView {
    @Inject MainActivityFragmentPresenter mainActivityFragmentPresenter;
    @BindView(R.id.fragment_main_pager)
    NonScrollableViewPager mViewPager;
    @BindView(R.id.fragment_main_tab_menu) NavigationTabBar navigationTabBar;

    @Override protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        if (mainActivityFragmentPresenter != null) {
            mainActivityFragmentPresenter.setView(this);
        }
        if (savedInstanceState == null) {
            setupViewPager();
        }
    }

    private void setupViewPager() {
        navigationTabBar.setModels(getModels());
        mainActivityFragmentPresenter.initializeAdaptor();
        navigationTabBar.setViewPager(mViewPager, 0);
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(mainActivityFragmentPresenter);
    }

    @Override public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter != null) {
            mViewPager.setAdapter(pagerAdapter);
            mViewPager.setOffscreenPageLimit(3);
        }
    }

    @SuppressWarnings("ConstantConditions") private List<NavigationTabBar.Model> getModels() {
        return new ArrayList<NavigationTabBar.Model>() {
            {
                add(new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_stats),
                        ContextCompat.getColor(getContext(), android.R.color.transparent)).build());
                add(new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_screen),
                        ContextCompat.getColor(getContext(), android.R.color.transparent)).build());
                add(new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_profile),
                        ContextCompat.getColor(getContext(), android.R.color.transparent)).build());
            }
        };
    }
}

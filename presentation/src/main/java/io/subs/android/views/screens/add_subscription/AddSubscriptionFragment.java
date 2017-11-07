package io.subs.android.views.screens.add_subscription;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import io.subs.android.R;
import io.subs.android.di.components.SubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class AddSubscriptionFragment extends BaseFragment
        implements AddSubscriptionPresenter.AddSubscriptionView {
    @SuppressWarnings("WeakerAccess") @Inject AddSubscriptionPresenter addSubscriptionPresenter;
    @SuppressWarnings("WeakerAccess") @BindView(R.id.fragment_add_subs_tabs) TabLayout mTabLayout;
    @SuppressWarnings("WeakerAccess") @BindView(R.id.fragment_add_subs_pager) ViewPager mViewPager;
    private SubscriptionListFragment.SubscriptionListListener subscriptionListListener;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddSubscriptionActivity) {
            this.subscriptionListListener =
                    (SubscriptionListFragment.SubscriptionListListener) context;
        }
    }

    @OnClick(R.id.fragment_add_subs_custom) void addCustomSubscription() {
        subscriptionListListener.onCustomSubscriberCreate();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_add_subscription;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        if (addSubscriptionPresenter != null) this.addSubscriptionPresenter.setView(this);
        if (savedInstanceState == null) {
            setupViewPager();
            loadSubscriptions();
        }
    }

    /**
     * Loads all subscriptions.
     */
    private void loadSubscriptions() {
        this.addSubscriptionPresenter.initialize();
    }

    private void setupViewPager() {
        mTabLayout.setupWithViewPager(mViewPager);
        addSubscriptionPresenter.initializeAdaptor();
    }

    @Override protected void injectDagger() {
        getComponent(SubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(addSubscriptionPresenter);
    }

    @Override public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter != null) {
            mViewPager.setAdapter(pagerAdapter);
        }
    }
}

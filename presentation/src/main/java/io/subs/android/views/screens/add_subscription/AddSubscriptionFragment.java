package io.subs.android.views.screens.add_subscription;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import io.subs.android.R;
import io.subs.android.di.components.SubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import io.subs.domain.models.Subscription;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class AddSubscriptionFragment extends BaseFragment
        implements AddSubscriptionPresenter.AddSubscriptionView {
    @Inject AddSubscriptionPresenter addSubscriptionPresenter;
    @BindView(R.id.fragment_add_subs_tabs) TabLayout mTabLayout;
    @BindView(R.id.fragment_add_subs_pager) ViewPager mViewPager;
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
        this.addSubscriptionPresenter.setView(this);
        setupViewPager();
    }

    private void setupViewPager() {
        mTabLayout.setupWithViewPager(mViewPager);
        addSubscriptionPresenter.initializeAdaptor();
    }

    @Override protected void injectDagger() {
        getComponent(SubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(addSubscriptionPresenter);
    }

    @Override public void createSubscription(Subscription subscription) {
        if (this.subscriptionListListener != null) {
            this.subscriptionListListener.onSubscriptionClicked(subscription);
        }
    }

    @Override public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter != null) {
            mViewPager.setAdapter(pagerAdapter);
        }
    }
}

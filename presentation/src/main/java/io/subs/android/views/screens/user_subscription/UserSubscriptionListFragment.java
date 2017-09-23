package io.subs.android.views.screens.user_subscription;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import io.subs.android.R;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import io.subs.android.views.component.TopPaddingDecoration;
import io.subs.domain.models.UserSubscription;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class UserSubscriptionListFragment extends BaseFragment
        implements UserSubscriptionListPresenter.UserSubscriptionListView {
    @BindView(R.id.fragment_add_subscription_list) RecyclerView rvSubscriptions;
    @Inject UserSubscriptionListPresenter userSubscriptionListPresenter;

    @Override protected int getLayout() {
        return R.layout.fragment_subscription_list;
    }

    @Override protected void initializeViews() {
        this.userSubscriptionListPresenter.setView(this);
        setupRecyclerView();
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(userSubscriptionListPresenter);
        if (savedInstanceState == null) {
            this.loadSubscriptions();
        }
    }

    @Override public void showLoading() {

    }

    @Override public void hideLoading() {

    }

    @Override public void showRetry() {

    }

    @Override public void hideRetry() {

    }

    @Override public void showError(String message) {
        this.showToastMessage(message);
    }

    private void setupRecyclerView() {
        this.userSubscriptionListPresenter.initializeAdaptor();
        this.rvSubscriptions.setLayoutManager(new LinearLayoutManager(context()));
        this.rvSubscriptions.addItemDecoration(new TopPaddingDecoration(60));
    }

    /**
     * Loads all subscriptions.
     */
    private void loadSubscriptions() {
        this.userSubscriptionListPresenter.initialize();
    }

    @Override public void createSubscription(UserSubscription subscription) {

    }

    @Override public void setAdapter(RecyclerView.Adapter addSubscriptionAdaptor) {
        if (addSubscriptionAdaptor != null) {
            rvSubscriptions.setAdapter(addSubscriptionAdaptor);
        }
    }
}

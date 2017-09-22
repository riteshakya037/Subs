package io.subs.android.views.screens.add_subscription;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import io.subs.android.R;
import io.subs.android.di.components.SubscriptionComponent;
import io.subs.android.views.BaseFragment;
import io.subs.android.views.component.TopPaddingDecoration;
import io.subs.domain.models.Subscription;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SubscriptionListFragment extends BaseFragment
        implements SubscriptionListPresenter.SubscriptionListView {
    @BindView(R.id.rv_subscription_list) RecyclerView rvSubscriptions;
    @Inject SubscriptionListPresenter subscriptionListPresenter;
    private SubscriptionListListener subscriptionListListener;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddSubscriptionActivity) {
            this.subscriptionListListener = (SubscriptionListListener) context;
        }
    }

    @Override protected int getLayout() {
        return R.layout.fragment_subscription_list;
    }

    @Override protected void initializeViews() {
        this.subscriptionListPresenter.setView(this);
        setupRecyclerView();
    }

    @Override protected void injectDagger() {
        getComponent(SubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(subscriptionListPresenter);
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
        this.subscriptionListPresenter.initializeAdaptor();
        this.rvSubscriptions.setLayoutManager(new LinearLayoutManager(context()));
        this.rvSubscriptions.addItemDecoration(new TopPaddingDecoration(60));
    }

    /**
     * Loads all subscriptions.
     */
    private void loadSubscriptions() {
        this.subscriptionListPresenter.initialize();
    }

    @Override public void createSubscription(Subscription subscription) {
        if (this.subscriptionListListener != null) {
            this.subscriptionListListener.onSubscriptionClicked(subscription);
        }
    }

    @Override public void setAdapter(RecyclerView.Adapter addSubscriptionAdaptor) {
        if (addSubscriptionAdaptor != null) {
            rvSubscriptions.setAdapter(addSubscriptionAdaptor);
        }
    }

    /**
     * Interface for listening list events.
     */
    public interface SubscriptionListListener {
        void onSubscriptionClicked(final Subscription subscription);
    }
}

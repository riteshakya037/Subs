package io.subs.android.views.screens.add_subscription;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import io.subs.android.R;
import io.subs.android.di.components.SubscriptionComponent;
import io.subs.android.views.BaseFragment;
import io.subs.android.views.adapters.AddSubscriptionAdaptor;
import io.subs.domain.models.Subscription;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SubscriptionListFragment extends BaseFragment implements SubscriptionListView {
    @BindView(R.id.rv_subscription_list) RecyclerView rvSubscriptions;
    @Inject AddSubscriptionAdaptor addSubscriptionAdaptor;
    @Inject SubscriptionListPresenterImpl subscriptionListPresenter;

    private SubscriptionListListener subscriptionListListener;
    private AddSubscriptionAdaptor.OnItemClickListener onItemClickListener =
            new AddSubscriptionAdaptor.OnItemClickListener() {
                @Override public void onItemClicked(Subscription subscription) {
                    if (subscriptionListPresenter != null && subscription != null) {
                        subscriptionListPresenter.onItemClicked(subscription);
                    }
                }
            };

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AddSubscriptionActivity) {
            this.subscriptionListListener = (SubscriptionListListener) activity;
        }
    }

    @Override protected void initializeViews() {
        setupRecyclerView();
    }

    @Override protected void injectDagger() {
        getComponent(SubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(subscriptionListPresenter);
        this.subscriptionListPresenter.setView(this);
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
        this.addSubscriptionAdaptor.setOnItemClickListener(onItemClickListener);
        this.rvSubscriptions.setLayoutManager(new LinearLayoutManager(context()));
        this.rvSubscriptions.setAdapter(addSubscriptionAdaptor);
    }

    /**
     * Loads all subscriptions.
     */
    private void loadSubscriptions() {
        this.subscriptionListPresenter.initialize();
    }

    @Override public void renderSubscriptions(
            SubscribeToSubscriptionUpdates.SubscriptionDto subscriptionDto) {
        if (subscriptionDto != null) {
            switch (subscriptionDto.getAction()) {
                case ADDED:
                    this.addSubscriptionAdaptor.addSubscription(subscriptionDto.getSubscription());
                    break;
                case UPDATED:
                    this.addSubscriptionAdaptor.updateSubscription(
                            subscriptionDto.getSubscription());
                    break;
                case REMOVED:
                    this.addSubscriptionAdaptor.removeSubscription(
                            subscriptionDto.getSubscription());
                    break;
            }
        }
    }

    @Override public void createSubscription(Subscription subscription) {
        if (this.subscriptionListListener != null) {
            this.subscriptionListListener.onSubscriptionClicked(subscription);
        }
    }

    /**
     * Interface for listening list events.
     */
    public interface SubscriptionListListener {
        void onSubscriptionClicked(final Subscription subscription);
    }
}

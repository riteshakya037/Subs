package io.subs.android.views.screens.add_subscription;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.subs.android.R;
import io.subs.android.di.components.SubscriptionComponent;
import io.subs.android.views.BaseFragment;
import io.subs.android.views.adapters.AddSubscriptionAdaptor;
import io.subs.domain.interactor.subscription.SubscribeToSubscriptionUpdates;
import io.subs.domain.models.Subscription;
import java.util.Collection;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SubscriptionListFragment extends BaseFragment implements SubscriptionListView {
    @BindView(R.id.rv_subscription_list) RecyclerView rvSubscriptions;
    @Inject AddSubscriptionAdaptor addSubscriptionAdaptor;
    @Inject SubscriptionListPresenter subscriptionListPresenter;

    private SubscriptionListListener subscriptionListListener;
    private AddSubscriptionAdaptor.OnItemClickListener onItemClickListener = userModel -> {
        if (subscriptionListPresenter != null && userModel != null) {
            subscriptionListPresenter.onItemClicked(userModel);
        }
    };

    public SubscriptionListFragment() {
        setRetainInstance(true);
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AddSubscriptionActivity) {
            this.subscriptionListListener = (SubscriptionListListener) activity;
        }
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View fragmentView =
                inflater.inflate(R.layout.fragment_subscription_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(SubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.subscriptionListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadSubscriptions();
        }
    }

    @Override public void onResume() {
        super.onResume();
        this.subscriptionListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.subscriptionListPresenter.pause();
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

    @Override public Context context() {
        return this.getActivity().getApplicationContext();
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

    @Override public void renderSubscriptions(Subscription subscriptions) {
        if (subscriptions != null) {
            this.addSubscriptionAdaptor.setSubscriptionCollection(subscriptions);
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

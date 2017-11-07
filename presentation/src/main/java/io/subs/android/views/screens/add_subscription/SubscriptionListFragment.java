package io.subs.android.views.screens.add_subscription;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.fernandocejas.arrow.checks.Preconditions;
import io.subs.android.R;
import io.subs.android.di.components.SubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import io.subs.android.views.component.TopPaddingDecoration;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.enums.SubscriptionType;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SubscriptionListFragment extends BaseFragment
        implements SubscriptionListPresenter.SubscriptionListView {
    private static final String ARGS_LOAD_TYPE = "load_type";
    @SuppressWarnings("WeakerAccess") @BindView(R.id.fragment_add_subscription_list) RecyclerView rvSubscriptions;
    @SuppressWarnings("WeakerAccess") @Inject SubscriptionListPresenter subscriptionListPresenter;
    private SubscriptionListListener subscriptionListListener;

    public static Fragment createInstance(SubscriptionType loadType) {
        SubscriptionListFragment subscriptionListFragment = new SubscriptionListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_LOAD_TYPE, loadType.name());
        subscriptionListFragment.setArguments(bundle);
        return subscriptionListFragment;
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddSubscriptionActivity) {
            this.subscriptionListListener = (SubscriptionListListener) context;
        }
    }

    @Override protected int getLayout() {
        return R.layout.fragment_subscription_list;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        if (subscriptionListListener != null) this.subscriptionListPresenter.setView(this);
        setupRecyclerView();
        this.registerPresenter(subscriptionListPresenter);
        if (savedInstanceState == null) {
            this.loadSubscriptions();
        }
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerPresenter(subscriptionListPresenter);
    }

    @Override protected void injectDagger() {
        getComponent(SubscriptionComponent.class).inject(this);
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
        this.rvSubscriptions.setLayoutManager(new LinearLayoutManager(getContext()));
        this.rvSubscriptions.addItemDecoration(new TopPaddingDecoration(60));
    }

    /**
     * Loads all subscriptions.
     */
    private void loadSubscriptions() {
        this.subscriptionListPresenter.initialize(currentLoadType());
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

    private SubscriptionType currentLoadType() {
        final Bundle arguments = getArguments();
        if (arguments != null) {
            Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
            return SubscriptionType.valueOf(arguments.getString(ARGS_LOAD_TYPE));
        }
        return SubscriptionType.ALL;
    }

    /**
     * Interface for listening list events.
     */
    public interface SubscriptionListListener {
        void onSubscriptionClicked(final Subscription subscription);

        void onCustomSubscriberCreate();
    }
}

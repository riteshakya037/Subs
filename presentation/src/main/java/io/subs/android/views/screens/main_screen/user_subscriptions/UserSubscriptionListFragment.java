package io.subs.android.views.screens.main_screen.user_subscriptions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.OnClick;
import io.subs.android.R;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import io.subs.android.views.component.CustomSpinnerView;
import io.subs.android.views.component.TopPaddingDecoration;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") public class UserSubscriptionListFragment extends BaseFragment
        implements UserSubscriptionListPresenter.UserSubscriptionListView {
    @BindView(R.id.fragment_user_subscription_list) RecyclerView rvSubscriptions;
    @BindView(R.id.fragment_user_subscription_add) ImageButton btnAddSubscription;
    @BindView(R.id.fragment_user_subscription_cycle) CustomSpinnerView svCycleSelection;
    @Inject UserSubscriptionListPresenter userSubscriptionListPresenter;

    public static Fragment createInstance() {
        return new UserSubscriptionListFragment();
    }

    @OnClick(R.id.fragment_user_subscription_add) void openAddSubscription() {
        userSubscriptionListPresenter.openAddSubscription();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_user_subscription_list;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        this.userSubscriptionListPresenter.setView(this);
        setupRecyclerView();
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(userSubscriptionListPresenter);
        if (savedInstanceState == null) {
            this.initializeData();
            this.loadSubscriptions();
            this.setupObservables();
        }
    }

    private void initializeData() {
        svCycleSelection.setData(this.userSubscriptionListPresenter.getCycleList());
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
        this.rvSubscriptions.setLayoutManager(new LinearLayoutManager(getContext()));
        this.rvSubscriptions.addItemDecoration(new TopPaddingDecoration(60));
    }

    private void setupObservables() {
        userSubscriptionListPresenter.initializeCycleObserver(
                svCycleSelection.getChangeObservable());
    }

    /**
     * Loads all subscriptions.
     */
    private void loadSubscriptions() {
        this.userSubscriptionListPresenter.initialize();
    }

    @Override public void setAdapter(RecyclerView.Adapter addSubscriptionAdaptor) {
        if (addSubscriptionAdaptor != null) {
            rvSubscriptions.setAdapter(addSubscriptionAdaptor);
        }
    }

    @Override public void isAddEnabled(boolean isValid) {
        btnAddSubscription.setVisibility(isValid ? View.VISIBLE : View.GONE);
    }
}

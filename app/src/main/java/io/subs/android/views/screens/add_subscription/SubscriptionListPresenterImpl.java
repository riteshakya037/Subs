package io.subs.android.views.screens.add_subscription;

import android.support.annotation.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.di.PerActivity;
import io.subs.android.exception.ErrorMessageFactory;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.views.base.Presenter;
import io.subs.domain.exception.DefaultErrorBundle;
import io.subs.domain.exception.ErrorBundle;
import io.subs.domain.models.Subscription;
import io.subs.domain.usecases.DefaultObserver;
import io.subs.domain.usecases.subscription.GetSubscriptionList;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity class SubscriptionListPresenterImpl extends BaseRxPresenter {

    private final GetSubscriptionList getSubscriptionList;
    private SubscribeToSubscriptionUpdates subscribeToSubscriptionUpdates;
    private SubscriptionListView viewListView;

    @Inject public SubscriptionListPresenterImpl(GetSubscriptionList getUserListUserCase,
            SubscribeToSubscriptionUpdates subscribeToSubscriptionUpdates) {
        this.getSubscriptionList = getUserListUserCase;
        this.subscribeToSubscriptionUpdates = subscribeToSubscriptionUpdates;
    }

    public void setView(@NonNull SubscriptionListView view) {
        this.viewListView = view;
    }

    @Override public void onStart() {
        manage(subscribeToSubscriptionUpdates.execute(new DisposableObserver<SubscriptionDto>() {
            @Override public void onNext(@NonNull SubscriptionDto subscriptionDto) {
                showUsersCollectionInView(subscriptionDto);
            }

            @Override public void onError(@NonNull Throwable throwable) {

            }

            @Override public void onComplete() {

            }
        }, null));
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.loadUserList();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSubscriptionList();
    }

    public void onItemClicked(Subscription subscription) {
        this.viewListView.createSubscription(subscription);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage =
                ErrorMessageFactory.create(this.viewListView.context(), errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showUsersCollectionInView(SubscriptionDto subscriptions) {
        this.viewListView.renderSubscriptions(subscriptions);
    }

    private void getSubscriptionList() {
        this.getSubscriptionList.execute(new UserListObserver(), null);
    }

    private final class UserListObserver extends DefaultObserver<Void> {

        @Override public void onComplete() {
            SubscriptionListPresenterImpl.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            SubscriptionListPresenterImpl.this.hideViewLoading();
            SubscriptionListPresenterImpl.this.showErrorMessage(
                    new DefaultErrorBundle((Exception) e));
            SubscriptionListPresenterImpl.this.showViewRetry();
        }

        @Override public void onNext(Void users) {
        }
    }
}

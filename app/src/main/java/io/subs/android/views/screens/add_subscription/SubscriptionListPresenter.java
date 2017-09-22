package io.subs.android.views.screens.add_subscription;

import android.support.annotation.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.di.PerActivity;
import io.subs.android.exception.ErrorMessageFactory;
import io.subs.android.views.base.Presenter;
import io.subs.domain.exception.DefaultErrorBundle;
import io.subs.domain.exception.ErrorBundle;
import io.subs.domain.interactor.DefaultObserver;
import io.subs.domain.interactor.subscription.GetSubscriptionList;
import io.subs.domain.interactor.subscription.SubscribeToSubscriptionUpdates;
import io.subs.domain.models.Subscription;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity class SubscriptionListPresenter implements Presenter {

    private final GetSubscriptionList getSubscriptionList;
    private SubscribeToSubscriptionUpdates subscribeToSubscriptionUpdates;
    private SubscriptionListView viewListView;

    @Inject public SubscriptionListPresenter(GetSubscriptionList getUserListUserCase,
            SubscribeToSubscriptionUpdates subscribeToSubscriptionUpdates) {
        this.getSubscriptionList = getUserListUserCase;
        this.subscribeToSubscriptionUpdates = subscribeToSubscriptionUpdates;
    }

    public void setView(@NonNull SubscriptionListView view) {
        this.viewListView = view;
    }

    @Override public void resume() {
        subscribeToSubscriptionUpdates.execute(new DisposableObserver<Subscription>() {
            @Override public void onNext(@NonNull Subscription subscription) {
                showUsersCollectionInView(subscription);
            }

            @Override public void onError(@NonNull Throwable throwable) {

            }

            @Override public void onComplete() {

            }
        }, null);
    }

    @Override public void pause() {
    }

    @Override public void destroy() {
        this.getSubscriptionList.dispose();
        this.subscribeToSubscriptionUpdates.dispose();
        this.viewListView = null;
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

    private void showUsersCollectionInView(Subscription subscriptions) {
        this.viewListView.renderSubscriptions(subscriptions);
    }

    private void getSubscriptionList() {
        this.getSubscriptionList.execute(new UserListObserver(), null);
    }

    private final class UserListObserver extends DefaultObserver<Void> {

        @Override public void onComplete() {
            SubscriptionListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            SubscriptionListPresenter.this.hideViewLoading();
            SubscriptionListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            SubscriptionListPresenter.this.showViewRetry();
        }

        @Override public void onNext(Void users) {
        }
    }
}

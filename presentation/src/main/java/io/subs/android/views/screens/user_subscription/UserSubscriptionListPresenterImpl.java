package io.subs.android.views.screens.user_subscription;

import android.support.annotation.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.di.PerActivity;
import io.subs.android.exception.ErrorMessageFactory;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.views.adapters.UserSubscriptionAdaptor;
import io.subs.android.views.base.Presenter;
import io.subs.domain.exception.DefaultErrorBundle;
import io.subs.domain.exception.ErrorBundle;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.usecases.DefaultObserver;
import io.subs.domain.usecases.user_subscriptions.GetUserSubscriptionList;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity public class UserSubscriptionListPresenterImpl extends BaseRxPresenter
        implements UserSubscriptionListPresenter {

    private final GetUserSubscriptionList getSubscriptionList;
    private UserSubscriptionAdaptor addSubscriptionAdaptor;
    private SubscribeToUserSubscriptionUpdates subscribeToSubscriptionUpdates;
    private UserSubscriptionListView viewListView;
    private UserSubscriptionAdaptor.OnItemClickListener onItemClickListener =
            new UserSubscriptionAdaptor.OnItemClickListener() {
                @Override public void onItemClicked(UserSubscription subscription) {
                    if (subscription != null) {
                        viewListView.createSubscription(subscription);
                    }
                }
            };

    @Inject public UserSubscriptionListPresenterImpl(UserSubscriptionAdaptor addSubscriptionAdaptor,
            GetUserSubscriptionList getUserListUserCase,
            SubscribeToUserSubscriptionUpdates subscribeToSubscriptionUpdates) {
        this.addSubscriptionAdaptor = addSubscriptionAdaptor;
        this.getSubscriptionList = getUserListUserCase;
        this.subscribeToSubscriptionUpdates = subscribeToSubscriptionUpdates;
    }

    public void setView(@NonNull UserSubscriptionListView userSubscriptionListView) {
        this.viewListView = userSubscriptionListView;
    }

    @Override public void onStart() {
        manage(subscribeToSubscriptionUpdates.execute(
                new DisposableObserver<UserSubscriptionDto>() {
                    @Override public void onNext(@NonNull UserSubscriptionDto subscriptionDto) {
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

    @Override public void initializeAdaptor() {
        this.addSubscriptionAdaptor.setOnItemClickListener(onItemClickListener);
        this.viewListView.setAdapter(addSubscriptionAdaptor);
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSubscriptionList();
    }

    public void onItemClicked(UserSubscription subscription) {
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

    private void showUsersCollectionInView(UserSubscriptionDto subscriptionDto) {
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

    private void getSubscriptionList() {
        this.getSubscriptionList.execute(new SubscriptionListObserver(),
                GetUserSubscriptionList.Params.forCase(Cycle.ALL));
    }

    private final class SubscriptionListObserver extends DefaultObserver<Void> {

        @Override public void onComplete() {
            UserSubscriptionListPresenterImpl.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            UserSubscriptionListPresenterImpl.this.hideViewLoading();
            UserSubscriptionListPresenterImpl.this.showErrorMessage(
                    new DefaultErrorBundle((Exception) e));
            UserSubscriptionListPresenterImpl.this.showViewRetry();
        }

        @Override public void onNext(Void users) {
        }
    }
}

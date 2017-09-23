package io.subs.android.views.screens.add_subscription;

import android.support.annotation.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.di.PerActivity;
import io.subs.android.exception.ErrorMessageFactory;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.views.adapters.AddSubscriptionAdaptor;
import io.subs.android.views.base.Presenter;
import io.subs.domain.exception.DefaultErrorBundle;
import io.subs.domain.exception.ErrorBundle;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.enums.SubscriptionType;
import io.subs.domain.usecases.DefaultObserver;
import io.subs.domain.usecases.subscription.GetSubscriptionList;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity public class SubscriptionListPresenterImpl extends BaseRxPresenter
        implements SubscriptionListPresenter {

    private final GetSubscriptionList getSubscriptionList;
    private AddSubscriptionAdaptor addSubscriptionAdaptor;
    private SubscribeToSubscriptionUpdates subscribeToSubscriptionUpdates;
    private SubscriptionListView viewListView;
    private AddSubscriptionAdaptor.OnItemClickListener onItemClickListener =
            new AddSubscriptionAdaptor.OnItemClickListener() {
                @Override public void onItemClicked(Subscription subscription) {
                    if (subscription != null) {
                        viewListView.createSubscription(subscription);
                    }
                }
            };

    @Inject public SubscriptionListPresenterImpl(AddSubscriptionAdaptor addSubscriptionAdaptor,
            GetSubscriptionList getUserListUserCase,
            SubscribeToSubscriptionUpdates subscribeToSubscriptionUpdates) {
        this.addSubscriptionAdaptor = addSubscriptionAdaptor;
        this.getSubscriptionList = getUserListUserCase;
        this.subscribeToSubscriptionUpdates = subscribeToSubscriptionUpdates;
    }

    public void setView(@NonNull SubscriptionListView subscriptionListView) {
        this.viewListView = subscriptionListView;
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

    public void initialize(SubscriptionType subscriptionType) {
        this.loadSubscriptionList(subscriptionType);
    }

    @Override public void initializeAdaptor() {
        this.addSubscriptionAdaptor.setOnItemClickListener(onItemClickListener);
        this.viewListView.setAdapter(addSubscriptionAdaptor);
    }

    private void loadSubscriptionList(SubscriptionType subscriptionType) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSubscriptionList(subscriptionType);
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

    private void showUsersCollectionInView(SubscriptionDto subscriptionDto) {
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

    private void getSubscriptionList(SubscriptionType subscriptionType) {
        this.getSubscriptionList.execute(new UserListObserver(),
                GetSubscriptionList.Params.forCase(subscriptionType));
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

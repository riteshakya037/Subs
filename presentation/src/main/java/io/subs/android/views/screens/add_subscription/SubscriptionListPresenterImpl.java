package io.subs.android.views.screens.add_subscription;

import android.support.annotation.NonNull;
import android.util.Log;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.exception.ErrorMessageFactory;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.views.adapters.AddSubscriptionAdaptor;
import io.subs.domain.exception.ErrorBundle;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.enums.SubscriptionType;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;
import javax.inject.Inject;

public class SubscriptionListPresenterImpl extends BaseRxPresenter
        implements SubscriptionListPresenter {

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
    private static final String TAG = "SubscriptionListPresent";
    private SubscriptionType subscriptionType;

    @Inject public SubscriptionListPresenterImpl(AddSubscriptionAdaptor addSubscriptionAdaptor,
            SubscribeToSubscriptionUpdates subscribeToSubscriptionUpdates) {
        this.addSubscriptionAdaptor = addSubscriptionAdaptor;
        this.subscribeToSubscriptionUpdates = subscribeToSubscriptionUpdates;
    }

    public void setView(@NonNull SubscriptionListView subscriptionListView) {
        this.viewListView = subscriptionListView;
    }

    public void initialize(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
        manage(subscribeToSubscriptionUpdates.execute(new DisposableObserver<SubscriptionDto>() {
            @Override public void onNext(@NonNull SubscriptionDto subscriptionDto) {
                showUsersCollectionInView(subscriptionDto);
            }

            @Override public void onError(@NonNull Throwable throwable) {

            }

            @Override public void onComplete() {

            }
        }, SubscribeToSubscriptionUpdates.Params.forCase(subscriptionType)));
        this.hideViewRetry();
        this.showViewLoading();
    }

    @Override public void initializeAdaptor() {
        this.addSubscriptionAdaptor.setOnItemClickListener(onItemClickListener);
        this.viewListView.setAdapter(addSubscriptionAdaptor);
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
        switch (subscriptionDto.getAction()) {
            case ADDED:
                this.addSubscriptionAdaptor.addSubscription(subscriptionDto.getSubscription());
                break;
            case UPDATED:
                this.addSubscriptionAdaptor.updateSubscription(subscriptionDto.getSubscription());
                break;
            case REMOVED:
                this.addSubscriptionAdaptor.removeSubscription(subscriptionDto.getSubscription());
                break;
        }
    }
}

package io.subs.android.views.screens.add_subscription;

import android.support.annotation.NonNull;
import android.util.Log;
import io.reactivex.observers.DisposableObserver;
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
public class SubscriptionListPresenterImpl extends BaseRxPresenter
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
    private static final String TAG = "SubscriptionListPresent";
    private SubscriptionType subscriptionType;

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
        }, null));
        this.loadSubscriptionList();
    }

    @Override public void initializeAdaptor() {
        this.addSubscriptionAdaptor.setOnItemClickListener(onItemClickListener);
        this.viewListView.setAdapter(addSubscriptionAdaptor);
    }

    private void loadSubscriptionList() {
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

    private void showUsersCollectionInView(SubscriptionDto subscriptionDto) {
        switch (subscriptionDto.getAction()) {
            case ADDED:
                Log.e(TAG, "showUsersCollectionInView: " + subscriptionType);
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

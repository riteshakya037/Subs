package io.subs.android.views.screens.main_screen.user_subscriptions;

import android.support.annotation.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.di.PerActivity;
import io.subs.android.exception.ErrorMessageFactory;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.views.adapters.UserSubscriptionAdaptor;
import io.subs.android.views.screens.main_screen.MainActivityFragmentPresenter;
import io.subs.domain.exception.DefaultErrorBundle;
import io.subs.domain.exception.ErrorBundle;
import io.subs.domain.models.UserProfile;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.usecases.DefaultObserver;
import io.subs.domain.usecases.session.GetUserProfile;
import io.subs.domain.usecases.user_subscriptions.GetUserSubscriptionList;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionCountUpdates;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import javax.inject.Inject;

@PerActivity public class UserSubscriptionListPresenterImpl extends BaseRxPresenter
        implements UserSubscriptionListPresenter {

    private final GetUserSubscriptionList getSubscriptionList;
    private final GetUserProfile getUserProfile;
    private final SubscribeToUserSubscriptionCountUpdates subscribeToUserSubscriptionCountUpdates;
    private UserSubscriptionAdaptor addSubscriptionAdaptor;
    private SubscribeToUserSubscriptionUpdates subscribeToSubscriptionUpdates;
    private MainActivityFragmentPresenter.MainActivityFlowListener mainActivityFlowListener;
    private UserSubscriptionListView viewListView;
    private UserSubscriptionAdaptor.OnItemClickListener onItemClickListener =
            new UserSubscriptionAdaptor.OnItemClickListener() {
                @Override public void onItemClicked(UserSubscription subscription) {
                    if (subscription != null) {
                        mainActivityFlowListener.createSubscription(subscription);
                    }
                }
            };
    private int maxSubs = 0;
    private int currentSubs = 0;

    @Inject public UserSubscriptionListPresenterImpl(UserSubscriptionAdaptor addSubscriptionAdaptor,
            GetUserSubscriptionList getUserListUserCase,
            SubscribeToUserSubscriptionUpdates subscribeToSubscriptionUpdates,
            MainActivityFragmentPresenter.MainActivityFlowListener mainActivityFlowListener,
            GetUserProfile getUserProfile,
            SubscribeToUserSubscriptionCountUpdates subscribeToUserSubscriptionCountUpdates) {
        this.addSubscriptionAdaptor = addSubscriptionAdaptor;
        this.getSubscriptionList = getUserListUserCase;
        this.subscribeToSubscriptionUpdates = subscribeToSubscriptionUpdates;
        this.mainActivityFlowListener = mainActivityFlowListener;
        this.getUserProfile = getUserProfile;
        this.subscribeToUserSubscriptionCountUpdates = subscribeToUserSubscriptionCountUpdates;
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
        getUserProfile();
        getUserSubscriptionCount();
    }

    private void getUserProfile() {
        manage(getUserProfile.execute(new DisposableObserver<UserProfile>() {
            @Override
            public void onNext(@io.reactivex.annotations.NonNull UserProfile userProfile) {
                maxSubs = userProfile.getSubAvailable();
                updateCounts();
            }

            @Override public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }

            @Override public void onComplete() {

            }
        }, null));
    }

    private void updateCounts() {
        viewListView.isAddEnabled(currentSubs < maxSubs);
    }

    private void getUserSubscriptionCount() {
        manage(subscribeToUserSubscriptionCountUpdates.execute(new DisposableObserver<Integer>() {
            @Override public void onNext(@io.reactivex.annotations.NonNull Integer integer) {
                currentSubs = integer;
                updateCounts();
            }

            @Override public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }

            @Override public void onComplete() {

            }
        }, null));
    }

    @Override public void initializeAdaptor() {
        this.addSubscriptionAdaptor.setOnItemClickListener(onItemClickListener);
        this.viewListView.setAdapter(addSubscriptionAdaptor);
    }

    @Override public void openAddSubscription() {
        mainActivityFlowListener.openAddSubscription();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSubscriptionList();
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
                GetUserSubscriptionList.Params.forCaseAll());
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

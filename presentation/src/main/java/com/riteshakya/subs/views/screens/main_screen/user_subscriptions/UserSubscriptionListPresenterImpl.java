package com.riteshakya.subs.views.screens.main_screen.user_subscriptions;

import android.content.Context;
import android.support.annotation.NonNull;

import com.riteshakya.subs.views.screens.main_screen.MainActivityFragmentPresenter;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.exception.ErrorMessageFactory;
import com.riteshakya.subs.mvp.BaseRxPresenter;
import com.riteshakya.subs.repository.SpinnerDataRepository;
import com.riteshakya.subs.views.adapters.UserSubscriptionAdaptor;
import com.riteshakya.subs.views.component.BaseSpinner;
import com.riteshakya.subs.views.screens.main_screen.MainActivityFragmentPresenter;
import com.riteshakya.domain.exception.DefaultErrorBundle;
import com.riteshakya.domain.exception.ErrorBundle;
import com.riteshakya.domain.models.UserProfile;
import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.domain.models.enums.Cycle;
import com.riteshakya.domain.usecases.DefaultObserver;
import com.riteshakya.domain.usecases.session.GetUserProfile;
import com.riteshakya.domain.usecases.user_subscriptions.GetUserSubscriptionList;
import com.riteshakya.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionCountUpdates;
import com.riteshakya.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;
import com.riteshakya.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Params;
import com.riteshakya.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@PerActivity public class UserSubscriptionListPresenterImpl extends BaseRxPresenter
        implements UserSubscriptionListPresenter {

    private final GetUserSubscriptionList getSubscriptionList;
    private final GetUserProfile getUserProfile;
    private final SubscribeToUserSubscriptionCountUpdates subscribeToUserSubscriptionCountUpdates;
    private final UserSubscriptionAdaptor addSubscriptionAdaptor;
    private final SubscribeToUserSubscriptionUpdates subscribeToSubscriptionUpdates;
    private final MainActivityFragmentPresenter.MainActivityFlowListener mainActivityFlowListener;
    private final UserSubscriptionAdaptor.OnItemClickListener onItemClickListener =
            new UserSubscriptionAdaptor.OnItemClickListener() {
                @Override public void onItemClicked(UserSubscription subscription) {
                    if (subscription != null) {
                        mainActivityFlowListener.createSubscription(subscription);
                    }
                }
            };
    private final Context mContext;
    private Disposable disposableUpdates;
    private UserSubscriptionListView viewListView;
    private int maxSubs = 0;
    private int currentSubs = 0;

    @Inject public UserSubscriptionListPresenterImpl(Context context,
            UserSubscriptionAdaptor addSubscriptionAdaptor,
            GetUserSubscriptionList getUserListUserCase,
            SubscribeToUserSubscriptionUpdates subscribeToSubscriptionUpdates,
            MainActivityFragmentPresenter.MainActivityFlowListener mainActivityFlowListener,
            GetUserProfile getUserProfile,
            SubscribeToUserSubscriptionCountUpdates subscribeToUserSubscriptionCountUpdates) {
        this.mContext = context;
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

    private void changeCycle(Cycle cycle) {
        if (addSubscriptionAdaptor != null) {
            addSubscriptionAdaptor.clearData();
        }
        if (disposableUpdates != null) {
            disposableUpdates.dispose();
        }
        disposableUpdates = subscribeToSubscriptionUpdates.execute(
                new DisposableObserver<UserSubscriptionDto>() {
                    @Override public void onNext(@NonNull UserSubscriptionDto subscriptionDto) {
                        showUsersCollectionInView(subscriptionDto);
                    }

                    @Override public void onError(@NonNull Throwable throwable) {

                    }

                    @Override public void onComplete() {

                    }
                }, cycle == null ? Params.forCaseAll() : Params.forCase(cycle));
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.loadUserList();
        getUserProfile();
        getUserSubscriptionCount();
    }

    @Override public List<BaseSpinner> getCycleList() {
        List<BaseSpinner> cycleList = new ArrayList<>(SpinnerDataRepository.getCycleList());
        cycleList.add(0, new BaseSpinner("All", "All"));
        return cycleList;
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

    @Override public void initializeCycleObserver(Observable<String> changeObservable) {
        manage(changeObservable.subscribe(
                s -> changeCycle(s.equals("All") ? null : Cycle.valueOf(s))));
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
        String errorMessage = ErrorMessageFactory.create(mContext, errorBundle.getException());
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
        this.getSubscriptionList.execute(new SubscriptionListObserver(), null);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (disposableUpdates != null) disposableUpdates.dispose();
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

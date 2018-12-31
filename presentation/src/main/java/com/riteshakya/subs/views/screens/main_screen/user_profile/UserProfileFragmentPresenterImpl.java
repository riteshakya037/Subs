package com.riteshakya.subs.views.screens.main_screen.user_profile;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import com.riteshakya.subs.mvp.BaseRxPresenter;
import com.riteshakya.domain.models.UserProfile;
import com.riteshakya.domain.usecases.session.GetUserProfile;
import com.riteshakya.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionCountUpdates;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */
public class UserProfileFragmentPresenterImpl extends BaseRxPresenter
        implements UserProfileFragmentPresenter {
    private UserProfileView userProfileView;
    private final UserProfileFlowListener userProfileFlowListener;
    private final GetUserProfile getUserProfile;
    private final SubscribeToUserSubscriptionCountUpdates subscribeToUserSubscriptionCountUpdates;
    private int maxSubs = 0;
    private int currentSubs = 0;

    @Inject public UserProfileFragmentPresenterImpl(UserProfileFlowListener userProfileFlowListener,
            GetUserProfile getUserProfile,
            SubscribeToUserSubscriptionCountUpdates subscribeToUserSubscriptionCountUpdates) {
        this.userProfileFlowListener = userProfileFlowListener;
        this.getUserProfile = getUserProfile;
        this.subscribeToUserSubscriptionCountUpdates = subscribeToUserSubscriptionCountUpdates;
    }

    @Override public void setView(UserProfileView mainActivityView) {
        this.userProfileView = mainActivityView;
    }

    @Override public void initialize() {
        getUserProfile();
        getUserSubscriptionCount();
    }

    @Override public void openSettings() {
        userProfileFlowListener.openSettings();
    }

    private void getUserProfile() {
        manage(getUserProfile.execute(new DisposableObserver<UserProfile>() {
            @Override public void onNext(@NonNull UserProfile userProfile) {
                userProfileView.setName(userProfile.getUserFullName());
                userProfileView.setEmail(userProfile.getUserEmail());
                maxSubs = userProfile.getSubAvailable();
                updateCounts();
            }

            @Override public void onError(@NonNull Throwable e) {

            }

            @Override public void onComplete() {

            }
        }, null));
    }

    private void updateCounts() {
        userProfileView.setMaxSubs(currentSubs, maxSubs);
    }

    private void getUserSubscriptionCount() {
        manage(subscribeToUserSubscriptionCountUpdates.execute(new DisposableObserver<Integer>() {
            @Override public void onNext(@NonNull Integer integer) {
                currentSubs = integer;
                updateCounts();
            }

            @Override public void onError(@NonNull Throwable e) {

            }

            @Override public void onComplete() {

            }
        }, null));
    }
}
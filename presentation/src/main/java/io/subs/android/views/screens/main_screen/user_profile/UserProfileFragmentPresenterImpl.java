package io.subs.android.views.screens.main_screen.user_profile;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.domain.models.UserProfile;
import io.subs.domain.usecases.session.GetUserProfile;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionCountUpdates;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */
public class UserProfileFragmentPresenterImpl extends BaseRxPresenter
        implements UserProfileFragmentPresenter {
    private UserProfileView userProfileView;
    private GetUserProfile getUserProfile;
    private SubscribeToUserSubscriptionCountUpdates subscribeToUserSubscriptionCountUpdates;
    private int maxSubs = 0;
    private int currentSubs = 0;

    @Inject public UserProfileFragmentPresenterImpl(GetUserProfile getUserProfile,
            SubscribeToUserSubscriptionCountUpdates subscribeToUserSubscriptionCountUpdates) {
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
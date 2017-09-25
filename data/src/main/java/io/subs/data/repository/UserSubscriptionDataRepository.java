package io.subs.data.repository;

import io.reactivex.Observable;
import io.subs.data.repository.datasource.user_subscriptions.UserSubscriptionDataStore;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.repository.ISubscriptionRepository;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.user_subscriptions.GetUserSubscriptionList;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link ISubscriptionRepository} for retrieving Subscription data.
 */
@Singleton public class UserSubscriptionDataRepository implements IUserSubscriptionRepository {

    private final UserSubscriptionDataStore subscriptionDataStore;

    /**
     * Constructs a {@link ISubscriptionRepository}.
     *
     * @param subscriptionDataStore A factory to construct different data source implementations.
     */
    @Inject UserSubscriptionDataRepository(UserSubscriptionDataStore subscriptionDataStore) {
        this.subscriptionDataStore = subscriptionDataStore;
    }

    @Override public Observable<Void> subscriptions(GetUserSubscriptionList.Params params) {
        //we always get all users from the cloud
        return subscriptionDataStore.subscriptionEntityList(params);
    }

    @Override
    public Observable<SubscribeToUserSubscriptionUpdates.UserSubscriptionDto> subscribe() {
        return subscriptionDataStore.subscribe();
    }

    @Override
    public Observable<Void> createOrUpdateSubscription(UserSubscription userSubscription) {
        return subscriptionDataStore.createOrUpdateSubscription(userSubscription);
    }
}

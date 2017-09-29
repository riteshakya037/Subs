package io.subs.data.repository;

import io.reactivex.Observable;
import io.subs.data.repository.datasource.user_subscriptions.UserSubscriptionDataStore;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.repository.ISubscriptionRepository;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Params;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import io.subs.domain.usecases.user_subscriptions.SubscriptionBreakdownUpdates;
import io.subs.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;
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

    @Override public Observable<Void> subscriptions() {
        //we always get all users from the cloud
        return subscriptionDataStore.subscriptionEntityList();
    }

    @Override public Observable<UserSubscriptionDto> subscribe(Params params) {
        return subscriptionDataStore.subscribe(params);
    }

    @Override
    public Observable<Void> createOrUpdateSubscription(UserSubscription userSubscription) {
        return subscriptionDataStore.createOrUpdateSubscription(userSubscription);
    }

    @Override public Observable<Void> deleteSubscription(String id) {
        return subscriptionDataStore.deleteSubscription(id);
    }

    @Override public Observable<Integer> subscribeToCount() {
        return subscriptionDataStore.subscribeToCount();
    }

    @Override public Observable<SubscriptionBreakdownUpdates.BreakdownDto> subscribeToBreakdown(
            SubscriptionBreakdownUpdates.Params params) {
        return subscriptionDataStore.subscribeToBreakdown(params);
    }

    @Override
    public Observable<Float> subscribeToExpenses(SubscriptionExpenseUpdates.Params params) {
        return subscriptionDataStore.subscribeToExpenses(params);
    }
}

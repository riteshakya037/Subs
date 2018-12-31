package com.riteshakya.data.repository;

import com.riteshakya.domain.repository.ISubscriptionRepository;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;
import com.riteshakya.domain.usecases.user_subscriptions.SubscriptionBreakdownUpdates;
import com.riteshakya.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;

import io.reactivex.Observable;
import com.riteshakya.data.repository.datasource.user_subscriptions.UserSubscriptionDataStore;
import com.riteshakya.domain.models.UserSubscription;

import java.util.List;

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
    @Inject
    UserSubscriptionDataRepository(UserSubscriptionDataStore subscriptionDataStore) {
        this.subscriptionDataStore = subscriptionDataStore;
    }

    @Override
    public Observable<Void> subscriptions() {
        //we always get all users from the cloud
        return subscriptionDataStore.subscriptionEntityList();
    }

    @Override
    public Observable<SubscribeToUserSubscriptionUpdates.UserSubscriptionDto> subscribe(SubscribeToUserSubscriptionUpdates.Params params) {
        return subscriptionDataStore.subscribe(params);
    }

    @Override
    public Observable<Void> createOrUpdateSubscription(UserSubscription userSubscription) {
        return subscriptionDataStore.createOrUpdateSubscription(userSubscription);
    }

    @Override
    public Observable<Void> deleteSubscription(String id) {
        return subscriptionDataStore.deleteSubscription(id);
    }

    @Override
    public Observable<Integer> subscribeToCount() {
        return subscriptionDataStore.subscribeToCount();
    }

    @Override
    public Observable<SubscriptionBreakdownUpdates.BreakdownDto> subscribeToBreakdown(
            SubscriptionBreakdownUpdates.Params params) {
        return subscriptionDataStore.subscribeToBreakdown(params);
    }

    @Override
    public Observable<Float> subscribeToExpenses(SubscriptionExpenseUpdates.Params params) {
        return subscriptionDataStore.subscribeToExpenses(params);
    }

    @Override
    public Observable<List<UserSubscription>> getSubscriptionsForToday() {
        return subscriptionDataStore.getSubscriptionsForToday();
    }
}

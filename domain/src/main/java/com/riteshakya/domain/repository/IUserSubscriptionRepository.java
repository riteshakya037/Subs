package com.riteshakya.domain.repository;

import com.riteshakya.domain.models.Subscription;
import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;
import com.riteshakya.domain.usecases.user_subscriptions.SubscriptionBreakdownUpdates;
import com.riteshakya.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting user {@link Subscription} related data.
 */
public interface IUserSubscriptionRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Subscription}.
     */
    Observable<Void> subscriptions();

    Observable<SubscribeToUserSubscriptionUpdates.UserSubscriptionDto> subscribe(SubscribeToUserSubscriptionUpdates.Params params);

    Observable<Void> createOrUpdateSubscription(UserSubscription userSubscription);

    Observable<Void> deleteSubscription(String id);

    Observable<Integer> subscribeToCount();

    Observable<SubscriptionBreakdownUpdates.BreakdownDto> subscribeToBreakdown(
            SubscriptionBreakdownUpdates.Params params);

    Observable<Float> subscribeToExpenses(SubscriptionExpenseUpdates.Params params);

    Observable<List<UserSubscription>> getSubscriptionsForToday();
}

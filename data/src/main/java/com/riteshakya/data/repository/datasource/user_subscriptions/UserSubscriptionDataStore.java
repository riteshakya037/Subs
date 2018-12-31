package com.riteshakya.data.repository.datasource.user_subscriptions;

import com.riteshakya.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;
import com.riteshakya.domain.usecases.user_subscriptions.SubscriptionBreakdownUpdates;
import com.riteshakya.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;

import io.reactivex.Observable;
import com.riteshakya.domain.models.UserSubscription;

import java.util.List;

/**
 * @author Ritesh Shakya
 */
public interface UserSubscriptionDataStore {
    Observable<Void> subscriptionEntityList();

    Observable<SubscribeToUserSubscriptionUpdates.UserSubscriptionDto> subscribe(
            SubscribeToUserSubscriptionUpdates.Params params);

    Observable<Void> createOrUpdateSubscription(UserSubscription userSubscription);

    Observable<Void> deleteSubscription(String id);

    Observable<Integer> subscribeToCount();

    Observable<SubscriptionBreakdownUpdates.BreakdownDto> subscribeToBreakdown(
            SubscriptionBreakdownUpdates.Params params);

    Observable<Float> subscribeToExpenses(SubscriptionExpenseUpdates.Params params);

    Observable<List<UserSubscription>> getSubscriptionsForToday();
}

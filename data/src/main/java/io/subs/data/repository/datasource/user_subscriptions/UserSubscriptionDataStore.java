package io.subs.data.repository.datasource.user_subscriptions;

import io.reactivex.Observable;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;
import io.subs.domain.usecases.user_subscriptions.SubscriptionBreakdownUpdates;
import io.subs.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;

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
}

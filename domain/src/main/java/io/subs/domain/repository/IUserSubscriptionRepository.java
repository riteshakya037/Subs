package io.subs.domain.repository;

import io.reactivex.Observable;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Params;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import io.subs.domain.usecases.user_subscriptions.SubscriptionBreakdownUpdates;
import io.subs.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;

/**
 * Interface that represents a Repository for getting user {@link Subscription} related data.
 */
public interface IUserSubscriptionRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Subscription}.
     */
    Observable<Void> subscriptions();

    Observable<UserSubscriptionDto> subscribe(Params params);

    Observable<Void> createOrUpdateSubscription(UserSubscription userSubscription);

    Observable<Void> deleteSubscription(String id);

    Observable<Integer> subscribeToCount();

    Observable<SubscriptionBreakdownUpdates.BreakdownDto> subscribeToBreakdown(
            SubscriptionBreakdownUpdates.Params params);

    Observable<Float> subscribeToExpenses(SubscriptionExpenseUpdates.Params params);
}

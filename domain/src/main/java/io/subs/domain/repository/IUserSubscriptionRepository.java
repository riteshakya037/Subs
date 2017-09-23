package io.subs.domain.repository;

import io.reactivex.Observable;
import io.subs.domain.models.Subscription;
import io.subs.domain.usecases.user_subscriptions.GetUserSubscriptionList;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;

/**
 * Interface that represents a Repository for getting user {@link Subscription} related data.
 */
public interface IUserSubscriptionRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Subscription}.
     */
    Observable<Void> subscriptions(GetUserSubscriptionList.Params params);

    Observable<SubscribeToUserSubscriptionUpdates.UserSubscriptionDto> subscribe();
}

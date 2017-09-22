package io.subs.domain.repository;

import io.reactivex.Observable;
import io.subs.domain.models.Subscription;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;

/**
 * Interface that represents a Repository for getting {@link Subscription} related data.
 */
public interface ISubscriptionRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Subscription}.
     */
    Observable<Void> subscriptions();

    Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe();
}

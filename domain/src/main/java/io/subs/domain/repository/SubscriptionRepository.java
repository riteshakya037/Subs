package io.subs.domain.repository;

import io.reactivex.Observable;
import io.subs.domain.models.Subscription;

/**
 * Interface that represents a Repository for getting {@link Subscription} related data.
 */
public interface SubscriptionRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Subscription}.
     */
    Observable<Void> subscriptions();

    Observable<Subscription> subscribe();
}

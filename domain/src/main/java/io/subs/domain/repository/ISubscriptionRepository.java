package io.subs.domain.repository;

import io.reactivex.Observable;
import io.subs.domain.models.Subscription;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.Params;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;

/**
 * Interface that represents a Repository for getting {@link Subscription} related data.
 */
public interface ISubscriptionRepository {
    Observable<Void> subscriptions();

    Observable<SubscriptionDto> subscribe(Params params);
}

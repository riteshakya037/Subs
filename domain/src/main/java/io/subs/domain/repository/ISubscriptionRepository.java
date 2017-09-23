package io.subs.domain.repository;

import io.reactivex.Observable;
import io.subs.domain.models.Subscription;
import io.subs.domain.usecases.subscription.GetSubscriptionList;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;

/**
 * Interface that represents a Repository for getting {@link Subscription} related data.
 */
public interface ISubscriptionRepository {
    Observable<Void> subscriptions(GetSubscriptionList.Params params);

    Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe();
}

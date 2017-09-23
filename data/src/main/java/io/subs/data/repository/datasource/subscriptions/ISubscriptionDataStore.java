package io.subs.data.repository.datasource.subscriptions;

import io.reactivex.Observable;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;

/**
 * @author Ritesh Shakya
 */
public interface ISubscriptionDataStore {
    Observable<Void> subscriptionEntityList();

    Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe();
}

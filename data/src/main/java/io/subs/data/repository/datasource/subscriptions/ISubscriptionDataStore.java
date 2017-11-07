package io.subs.data.repository.datasource.subscriptions;

import io.reactivex.Observable;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.Params;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;

/**
 * @author Ritesh Shakya
 */
public interface ISubscriptionDataStore {
    Observable<Void> subscriptionEntityList();

    Observable<SubscriptionDto> subscribe(Params params);
}

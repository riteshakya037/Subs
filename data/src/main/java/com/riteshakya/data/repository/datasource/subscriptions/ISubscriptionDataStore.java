package com.riteshakya.data.repository.datasource.subscriptions;

import com.riteshakya.domain.usecases.subscription.SubscribeToSubscriptionUpdates;

import io.reactivex.Observable;
import com.riteshakya.domain.usecases.subscription.SubscribeToSubscriptionUpdates.Params;
import com.riteshakya.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;

/**
 * @author Ritesh Shakya
 */
public interface ISubscriptionDataStore {
    Observable<Void> subscriptionEntityList();

    Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe(SubscribeToSubscriptionUpdates.Params params);
}

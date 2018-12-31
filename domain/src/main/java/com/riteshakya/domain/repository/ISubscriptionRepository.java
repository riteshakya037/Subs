package com.riteshakya.domain.repository;

import com.riteshakya.domain.models.Subscription;
import com.riteshakya.domain.usecases.subscription.SubscribeToSubscriptionUpdates;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Subscription} related data.
 */
public interface ISubscriptionRepository {
    Observable<Void> subscriptions();

    Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe(SubscribeToSubscriptionUpdates.Params params);
}

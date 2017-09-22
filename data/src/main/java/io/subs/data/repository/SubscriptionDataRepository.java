package io.subs.data.repository;

import io.reactivex.Observable;
import io.subs.data.repository.datasource.SubscriptionDataStore;
import io.subs.domain.repository.ISubscriptionRepository;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link ISubscriptionRepository} for retrieving Subscription data.
 */
@Singleton public class SubscriptionDataRepository implements ISubscriptionRepository {

    private final SubscriptionDataStore subscriptionDataStore;

    /**
     * Constructs a {@link ISubscriptionRepository}.
     *
     * @param subscriptionDataStore A factory to construct different data source implementations.
     */
    @Inject SubscriptionDataRepository(SubscriptionDataStore subscriptionDataStore) {
        this.subscriptionDataStore = subscriptionDataStore;
    }

    @Override public Observable<Void> subscriptions() {
        //we always get all users from the cloud
        return subscriptionDataStore.subscriptionEntityList();
    }

    @Override public Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe() {
        return subscriptionDataStore.subscribe();
    }
}

package io.subs.data.repository;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.subs.data.entity.SubscriptionEntity;
import io.subs.data.entity.mapper.SubscriptionEntityDataMapper;
import io.subs.data.repository.datasource.SubscriptionDataStore;
import io.subs.domain.models.Subscription;
import io.subs.domain.repository.ISubscriptionRepository;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link ISubscriptionRepository} for retrieving Subscription data.
 */
@Singleton public class SubscriptionDataRepository implements ISubscriptionRepository {

    private final SubscriptionDataStore subscriptionDataStore;
    private final SubscriptionEntityDataMapper subscriptionEntityDataMapper;

    /**
     * Constructs a {@link ISubscriptionRepository}.
     *
     * @param subscriptionDataStore A factory to construct different data source implementations.
     * @param subscriptionEntityDataMapper {@link SubscriptionEntityDataMapper}.
     */
    @Inject SubscriptionDataRepository(SubscriptionDataStore subscriptionDataStore,
            SubscriptionEntityDataMapper subscriptionEntityDataMapper) {
        this.subscriptionDataStore = subscriptionDataStore;
        this.subscriptionEntityDataMapper = subscriptionEntityDataMapper;
    }

    @Override public Observable<Void> subscriptions() {
        //we always get all users from the cloud
        return subscriptionDataStore.subscriptionEntityList();
    }

    @Override public Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe() {
        return subscriptionDataStore.subscribe();
    }
}

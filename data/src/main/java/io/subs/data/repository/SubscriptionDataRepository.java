package io.subs.data.repository;

import io.reactivex.Observable;
import io.subs.data.repository.datasource.subscriptions.ISubscriptionDataStore;
import io.subs.domain.repository.ISubscriptionRepository;
import io.subs.domain.usecases.subscription.GetSubscriptionList;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link ISubscriptionRepository} for retrieving Subscription data.
 */
@Singleton public class SubscriptionDataRepository implements ISubscriptionRepository {

    private final ISubscriptionDataStore subscriptionDataStore;

    /**
     * Constructs a {@link ISubscriptionRepository}.
     *
     * @param subscriptionDataStore A factory to construct different data source implementations.
     */
    @Inject SubscriptionDataRepository(ISubscriptionDataStore subscriptionDataStore) {
        this.subscriptionDataStore = subscriptionDataStore;
    }

    @Override public Observable<Void> subscriptions(GetSubscriptionList.Params params) {
        //we always get all users from the cloud
        return subscriptionDataStore.subscriptionEntityList(params);
    }

    @Override public Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe() {
        return subscriptionDataStore.subscribe();
    }
}

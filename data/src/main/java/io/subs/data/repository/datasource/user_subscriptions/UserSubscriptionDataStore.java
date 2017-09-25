package io.subs.data.repository.datasource.user_subscriptions;

import io.reactivex.Observable;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.usecases.user_subscriptions.GetUserSubscriptionList;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates;

/**
 * @author Ritesh Shakya
 */
public interface UserSubscriptionDataStore {
    Observable<Void> subscriptionEntityList(GetUserSubscriptionList.Params params);

    Observable<SubscribeToUserSubscriptionUpdates.UserSubscriptionDto> subscribe();

    Observable<Void> createOrUpdateSubscription(UserSubscription userSubscription);
}

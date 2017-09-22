package io.subs.data.entity.mapper;

import io.subs.data.entity.SubscriptionEntity;
import io.subs.domain.models.Subscription;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link SubscriptionEntity} (in the data layer) to {@link
 * Subscription} in the
 * domain layer.
 */
@Singleton public class SubscriptionEntityDataMapper {

    @Inject SubscriptionEntityDataMapper() {
    }

    /**
     * Transform a {@link SubscriptionEntity} into an {@link Subscription}.
     *
     * @param subscriptionEntity Object to be transformed.
     * @return {@link Subscription} if valid {@link SubscriptionEntity} otherwise null.
     */
    public Subscription transform(SubscriptionEntity subscriptionEntity) {
        Subscription subscription = null;
        if (subscriptionEntity != null) {
            subscription = new Subscription();
            subscription.setColor(subscriptionEntity.getColor());
            subscription.setIcon(subscriptionEntity.getIcon());
            subscription.setName(subscriptionEntity.getName());
        }
        return subscription;
    }

    /**
     * Transform a List of {@link SubscriptionEntity} into a Collection of {@link Subscription}.
     *
     * @param subscriptionEntities Object Collection to be transformed.
     * @return {@link Subscription} if valid {@link SubscriptionEntity} otherwise null.
     */
    public List<Subscription> transform(Collection<SubscriptionEntity> subscriptionEntities) {
        final List<Subscription> subscriptions = new ArrayList<>(20);
        for (SubscriptionEntity subscriptionEntity : subscriptionEntities) {
            final Subscription subscription = transform(subscriptionEntity);
            if (subscription != null) {
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }
}

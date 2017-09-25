package io.subs.domain.usecases.subscription;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.enums.SubscriptionType;
import io.subs.domain.repository.ISubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscribeToSubscriptionUpdates extends
        UseCase<SubscribeToSubscriptionUpdates.SubscriptionDto, SubscribeToSubscriptionUpdates.Params> {

    private ISubscriptionRepository subscriptionRepository;

    @Inject SubscribeToSubscriptionUpdates(ISubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<SubscriptionDto> buildUseCaseObservable(Params params) {
        return subscriptionRepository.subscribe(params);
    }

    public enum Action {
        ADDED, UPDATED, REMOVED
    }

    public static class SubscriptionDto {
        private final Subscription subscription;
        private final Action mAction;

        public SubscriptionDto(@NonNull Subscription subscription, @NonNull Action action) {
            this.subscription = subscription;
            this.mAction = action;
        }

        public Subscription getSubscription() {
            return subscription;
        }

        public Action getAction() {
            return mAction;
        }
    }

    public static class Params {
        private SubscriptionType subscriptionType;

        public Params(SubscriptionType subscriptionType) {
            this.subscriptionType = subscriptionType;
        }

        public static Params forCase(SubscriptionType subscriptionType) {
            return new Params(subscriptionType);
        }

        public SubscriptionType getSubscriptionType() {
            return subscriptionType;
        }
    }
}

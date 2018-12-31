package com.riteshakya.domain.usecases.subscription;

import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.models.Subscription;
import com.riteshakya.domain.models.enums.SubscriptionType;
import com.riteshakya.domain.repository.ISubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscribeToSubscriptionUpdates extends
        UseCase<SubscribeToSubscriptionUpdates.SubscriptionDto, SubscribeToSubscriptionUpdates.Params> {

    private final ISubscriptionRepository subscriptionRepository;

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
        private final SubscriptionType subscriptionType;

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

package io.subs.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscribeToUserSubscriptionUpdates extends
        UseCase<SubscribeToUserSubscriptionUpdates.UserSubscriptionDto, SubscribeToUserSubscriptionUpdates.Params> {

    private final IUserSubscriptionRepository subscriptionRepository;

    @Inject SubscribeToUserSubscriptionUpdates(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<UserSubscriptionDto> buildUseCaseObservable(Params params) {
        return subscriptionRepository.subscribe(params);
    }

    public enum Action {
        ADDED, UPDATED, REMOVED
    }

    public static class UserSubscriptionDto {
        private final UserSubscription subscription;
        private final Action mAction;

        public UserSubscriptionDto(@NonNull UserSubscription subscription, @NonNull Action action) {
            this.subscription = subscription;
            this.mAction = action;
        }

        public UserSubscription getSubscription() {
            return subscription;
        }

        public Action getAction() {
            return mAction;
        }
    }

    public static class Params {
        private Cycle subscriptionCycle;
        private final boolean isAll;

        public Params(Cycle subscriptionCycle) {
            this.subscriptionCycle = subscriptionCycle;
            this.isAll = false;
        }

        public Params() {
            this.isAll = true;
        }

        public static Params forCase(Cycle subscriptionCycle) {
            return new Params(subscriptionCycle);
        }

        public static Params forCaseAll() {
            return new Params();
        }

        public Cycle getSubscriptionCycle() {
            return subscriptionCycle;
        }

        public boolean isAll() {
            return isAll;
        }
    }
}

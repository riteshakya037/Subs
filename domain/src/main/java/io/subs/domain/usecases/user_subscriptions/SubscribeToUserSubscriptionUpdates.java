package io.subs.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscribeToUserSubscriptionUpdates
        extends UseCase<SubscribeToUserSubscriptionUpdates.UserSubscriptionDto, Void> {

    private IUserSubscriptionRepository subscriptionRepository;

    @Inject SubscribeToUserSubscriptionUpdates(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<UserSubscriptionDto> buildUseCaseObservable(Void params) {
        return subscriptionRepository.subscribe();
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
}

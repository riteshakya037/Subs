package io.subs.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscribeToUserSubscriptionCountUpdates extends UseCase<Integer, Void> {

    private IUserSubscriptionRepository subscriptionRepository;

    @Inject SubscribeToUserSubscriptionCountUpdates(
            IUserSubscriptionRepository subscriptionRepository, IThreadExecutor threadExecutor,
            IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Integer> buildUseCaseObservable(Void params) {
        return subscriptionRepository.subscribeToCount();
    }
}

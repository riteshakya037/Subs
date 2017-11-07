package io.subs.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class DeleteSubscription extends UseCase<Void, String> {
    private final IUserSubscriptionRepository subscriptionRepository;

    @Inject public DeleteSubscription(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Void> buildUseCaseObservable(String userID) {
        return subscriptionRepository.deleteSubscription(userID);
    }
}

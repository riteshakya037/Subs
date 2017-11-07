package io.subs.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class CreateOrUpdateSubscription extends UseCase<Void, UserSubscription> {
    private final IUserSubscriptionRepository subscriptionRepository;

    @Inject public CreateOrUpdateSubscription(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Void> buildUseCaseObservable(UserSubscription userSubscription) {
        return subscriptionRepository.createOrUpdateSubscription(userSubscription);
    }
}

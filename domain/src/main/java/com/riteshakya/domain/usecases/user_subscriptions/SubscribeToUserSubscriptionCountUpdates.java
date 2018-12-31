package com.riteshakya.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscribeToUserSubscriptionCountUpdates extends UseCase<Integer, Void> {

    private final IUserSubscriptionRepository subscriptionRepository;

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

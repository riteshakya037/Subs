package com.riteshakya.domain.usecases.user_subscriptions;

import com.riteshakya.domain.repository.IUserSubscriptionRepository;

import io.reactivex.Observable;
import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;
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

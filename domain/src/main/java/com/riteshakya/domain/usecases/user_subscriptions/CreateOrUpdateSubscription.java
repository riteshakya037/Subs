package com.riteshakya.domain.usecases.user_subscriptions;

import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

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

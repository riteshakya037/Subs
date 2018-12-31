package com.riteshakya.domain.usecases.user_subscriptions;

import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class GetSubscriptionsForToday extends
        UseCase<List<UserSubscription>, Void> {

    private final IUserSubscriptionRepository subscriptionRepository;

    @Inject
    GetSubscriptionsForToday(IUserSubscriptionRepository subscriptionRepository,
                             IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Observable<List<UserSubscription>> buildUseCaseObservable(Void params) {
        return subscriptionRepository.getSubscriptionsForToday();
    }
}

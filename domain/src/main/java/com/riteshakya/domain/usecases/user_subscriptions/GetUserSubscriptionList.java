package com.riteshakya.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.models.Subscription;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Subscription}.
 */
public class GetUserSubscriptionList extends UseCase<Void, Void> {

    private final IUserSubscriptionRepository subscriptionRepository;

    @Inject GetUserSubscriptionList(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Void> buildUseCaseObservable(Void params) {
        return this.subscriptionRepository.subscriptions();
    }
}

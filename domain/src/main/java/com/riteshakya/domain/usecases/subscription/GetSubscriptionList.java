package com.riteshakya.domain.usecases.subscription;

import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.models.Subscription;
import com.riteshakya.domain.repository.ISubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Subscription}.
 */
public class GetSubscriptionList extends UseCase<Void, Void> {

    private final ISubscriptionRepository subscriptionRepository;

    @Inject GetSubscriptionList(ISubscriptionRepository subscriptionRepository,
                                IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Void> buildUseCaseObservable(Void aVoid) {
        return this.subscriptionRepository.subscriptions();
    }
}

package io.subs.domain.interactor.subscription;

import io.reactivex.Observable;
import io.subs.domain.executor.PostExecutionThread;
import io.subs.domain.executor.ThreadExecutor;
import io.subs.domain.interactor.UseCase;
import io.subs.domain.models.Subscription;
import io.subs.domain.repository.SubscriptionRepository;
import java.util.List;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Subscription}.
 */
public class GetSubscriptionList extends UseCase<Void, Void> {

    private final SubscriptionRepository subscriptionRepository;

    @Inject GetSubscriptionList(SubscriptionRepository subscriptionRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Void> buildUseCaseObservable(Void unused) {
        return this.subscriptionRepository.subscriptions();
    }
}

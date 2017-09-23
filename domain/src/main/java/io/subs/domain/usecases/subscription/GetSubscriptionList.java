package io.subs.domain.usecases.subscription;

import io.reactivex.Observable;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.enums.SubscriptionType;
import io.subs.domain.repository.ISubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Subscription}.
 */
public class GetSubscriptionList extends UseCase<Void, GetSubscriptionList.Params> {

    private final ISubscriptionRepository subscriptionRepository;

    @Inject GetSubscriptionList(ISubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Void> buildUseCaseObservable(Params params) {
        return this.subscriptionRepository.subscriptions(params);
    }

    public static class Params {
        private SubscriptionType subscriptionType;

        public Params(SubscriptionType subscriptionType) {
            this.subscriptionType = subscriptionType;
        }

        public static Params forCase(SubscriptionType subscriptionType) {
            return new Params(subscriptionType);
        }

        public SubscriptionType getSubscriptionType() {
            return subscriptionType;
        }
    }
}

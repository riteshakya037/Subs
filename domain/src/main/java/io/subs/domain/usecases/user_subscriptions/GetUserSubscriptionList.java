package io.subs.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Subscription}.
 */
public class GetUserSubscriptionList extends UseCase<Void, GetUserSubscriptionList.Params> {

    private final IUserSubscriptionRepository subscriptionRepository;

    @Inject GetUserSubscriptionList(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Observable<Void> buildUseCaseObservable(GetUserSubscriptionList.Params params) {
        return this.subscriptionRepository.subscriptions(params);
    }

    public static class Params {
        private Cycle subscriptionCycle;
        private boolean isAll;

        public Params(Cycle subscriptionCycle) {
            this.subscriptionCycle = subscriptionCycle;
            this.isAll = false;
        }

        public Params() {
            this.isAll = true;
        }

        public static Params forCase(Cycle subscriptionCycle) {
            return new Params(subscriptionCycle);
        }

        public static Params forCaseAll() {
            return new Params();
        }

        public Cycle getSubscriptionCycle() {
            return subscriptionCycle;
        }

        public boolean isAll() {
            return isAll;
        }
    }
}

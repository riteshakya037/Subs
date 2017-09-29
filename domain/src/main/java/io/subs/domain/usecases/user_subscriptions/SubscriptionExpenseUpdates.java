package io.subs.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.repository.IUserSubscriptionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscriptionExpenseUpdates extends UseCase<Float, SubscriptionExpenseUpdates.Params> {

    private IUserSubscriptionRepository subscriptionRepository;

    @Inject SubscriptionExpenseUpdates(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Float> buildUseCaseObservable(Params params) {
        return subscriptionRepository.subscribeToExpenses(params);
    }

    public static class Params {
        private Cycle subscriptionCycle;

        public Params(Cycle subscriptionCycle) {
            this.subscriptionCycle = subscriptionCycle;
        }

        public static Params forCase(Cycle subscriptionCycle) {
            return new Params(subscriptionCycle);
        }

        public Cycle getSubscriptionCycle() {
            return subscriptionCycle;
        }
    }
}

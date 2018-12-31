package com.riteshakya.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.models.enums.Cycle;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscriptionExpenseUpdates extends UseCase<Float, SubscriptionExpenseUpdates.Params> {

    private final IUserSubscriptionRepository subscriptionRepository;

    @Inject SubscriptionExpenseUpdates(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Float> buildUseCaseObservable(Params params) {
        return subscriptionRepository.subscribeToExpenses(params);
    }

    public static class Params {
        private final Cycle subscriptionCycle;

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

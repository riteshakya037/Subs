package com.riteshakya.domain.usecases.user_subscriptions;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.models.enums.Cycle;
import com.riteshakya.domain.models.usecase_dtos.BreakdownModel;
import com.riteshakya.domain.repository.IUserSubscriptionRepository;
import com.riteshakya.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscriptionBreakdownUpdates extends
        UseCase<SubscriptionBreakdownUpdates.BreakdownDto, SubscriptionBreakdownUpdates.Params> {

    private final IUserSubscriptionRepository subscriptionRepository;

    @Inject SubscriptionBreakdownUpdates(IUserSubscriptionRepository subscriptionRepository,
            IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<BreakdownDto> buildUseCaseObservable(Params params) {
        return subscriptionRepository.subscribeToBreakdown(params);
    }

    public static class BreakdownDto {
        private final float[] mData;

        public BreakdownDto(@NonNull BreakdownModel breakdownModel) {
            this.mData = breakdownModel.getData();
        }

        public float[] getData() {
            return mData;
        }
    }

    public static class Params {
        private final Cycle subscriptionCycle;

        public Params(Cycle subscriptionCycle) {
            this.subscriptionCycle = subscriptionCycle;
        }

        public static SubscriptionBreakdownUpdates.Params forCase(Cycle subscriptionCycle) {
            return new SubscriptionBreakdownUpdates.Params(subscriptionCycle);
        }

        public Cycle getSubscriptionCycle() {
            return subscriptionCycle;
        }
    }
}

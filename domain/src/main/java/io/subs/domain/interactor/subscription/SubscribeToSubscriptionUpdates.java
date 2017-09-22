package io.subs.domain.interactor.subscription;

import io.reactivex.Observable;
import io.subs.domain.executor.PostExecutionThread;
import io.subs.domain.executor.ThreadExecutor;
import io.subs.domain.interactor.UseCase;
import io.subs.domain.models.Subscription;
import io.subs.domain.repository.SubscriptionRepository;
import javax.inject.Inject;

/**
 * Use case for subscribing to new messages in a conversation
 */
public class SubscribeToSubscriptionUpdates
        extends UseCase<Subscription, SubscribeToSubscriptionUpdates.Params> {

    private SubscriptionRepository subscriptionRepository;

    @Inject SubscribeToSubscriptionUpdates(SubscriptionRepository subscriptionRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override public Observable<Subscription> buildUseCaseObservable(
            SubscribeToSubscriptionUpdates.Params params) {
        return subscriptionRepository.subscribe();
    }

    public Params createParams(String conversationId) {
        return new Params(conversationId);
    }

    public class Params {
        String conversationId;

        public Params(String conversationId) {
            this.conversationId = conversationId;
        }
    }
}

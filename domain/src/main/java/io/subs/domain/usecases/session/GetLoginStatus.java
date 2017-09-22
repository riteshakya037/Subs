package io.subs.domain.usecases.session;

import io.reactivex.Observable;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.repository.ISessionRepository;
import io.subs.domain.usecases.UseCase;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class GetLoginStatus extends UseCase<GetLoginStatus.LoginStatusType, Void> {
    private final ISessionRepository sessionRepository;

    @Inject GetLoginStatus(ISessionRepository sessionRepository, IThreadExecutor threadExecutor,
            IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.sessionRepository = sessionRepository;
    }

    @Override public Observable<LoginStatusType> buildUseCaseObservable(Void aVoid) {
        return this.sessionRepository.getLoginStatus();
    }

    public enum LoginStatusType {
        ACTIVE, INACTIVE;
    }
}

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
public class SignOut extends UseCase<Void, Void> {
    private final ISessionRepository sessionRepository;

    @Inject public SignOut(ISessionRepository sessionRepository, IThreadExecutor threadExecutor,
            IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.sessionRepository = sessionRepository;
    }

    @Override public Observable<Void> buildUseCaseObservable(Void aVoid) {
        return sessionRepository.signOut();
    }
}

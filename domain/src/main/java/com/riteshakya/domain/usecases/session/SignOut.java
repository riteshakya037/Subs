package com.riteshakya.domain.usecases.session;

import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.repository.ISessionRepository;
import com.riteshakya.domain.usecases.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

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

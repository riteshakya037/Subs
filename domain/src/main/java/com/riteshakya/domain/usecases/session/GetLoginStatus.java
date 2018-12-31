package com.riteshakya.domain.usecases.session;

import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.repository.ISessionRepository;
import com.riteshakya.domain.usecases.UseCase;

import io.reactivex.Observable;
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
        ACTIVE, INACTIVE
    }
}

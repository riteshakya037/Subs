package com.riteshakya.domain.usecases.session;

import com.riteshakya.domain.executor.IPostExecutionThread;
import com.riteshakya.domain.executor.IThreadExecutor;
import com.riteshakya.domain.models.UserProfile;
import com.riteshakya.domain.repository.ISessionRepository;
import com.riteshakya.domain.usecases.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ritesh Shakya
 */

public class GetUserProfile extends UseCase<UserProfile, Void> {
    private final ISessionRepository sessionRepository;

    @Inject GetUserProfile(ISessionRepository sessionRepository, IThreadExecutor threadExecutor,
            IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.sessionRepository = sessionRepository;
    }

    @Override public Observable<UserProfile> buildUseCaseObservable(Void aVoid) {
        return this.sessionRepository.getProfile();
    }
}

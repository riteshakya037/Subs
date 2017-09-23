package io.subs.data.repository;

import io.reactivex.Observable;
import io.subs.data.repository.datasource.sessions.ISessionDataStore;
import io.subs.domain.repository.ISessionRepository;
import io.subs.domain.usecases.session.GetLoginStatus;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ritesh Shakya
 */
@Singleton public class SessionDataRepository implements ISessionRepository {
    private final ISessionDataStore dataStoreFactory;

    @Inject SessionDataRepository(ISessionDataStore dataStoreFactory) {
        this.dataStoreFactory = dataStoreFactory;
    }

    @Override public Observable<GetLoginStatus.LoginStatusType> getLoginStatus() {
        return dataStoreFactory.getLoginStatus();
    }

    @Override public Observable<Void> signOut() {
        return dataStoreFactory.signOut();
    }
}

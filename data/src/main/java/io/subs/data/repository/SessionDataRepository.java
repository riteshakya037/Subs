package io.subs.data.repository;

import io.reactivex.Observable;
import io.subs.data.repository.datasource.SessionDataStore;
import io.subs.domain.repository.ISessionRepository;
import io.subs.domain.usecases.session.GetLoginStatus;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ritesh Shakya
 */
@Singleton public class SessionDataRepository implements ISessionRepository {
    private final SessionDataStore dataStoreFactory;

    @Inject SessionDataRepository(SessionDataStore dataStoreFactory) {
        this.dataStoreFactory = dataStoreFactory;
    }

    @Override public Observable<GetLoginStatus.LoginStatusType> getLoginStatus() {
        return dataStoreFactory.getLoginStatus();
    }

    @Override public Observable<Void> signOut() {
        return dataStoreFactory.signOut();
    }
}

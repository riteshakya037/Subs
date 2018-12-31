package com.riteshakya.data.repository;

import com.riteshakya.domain.repository.ISessionRepository;
import com.riteshakya.domain.usecases.session.GetLoginStatus;

import io.reactivex.Observable;
import com.riteshakya.data.repository.datasource.sessions.ISessionDataStore;
import com.riteshakya.domain.models.UserProfile;
import com.riteshakya.domain.repository.ISessionRepository;
import com.riteshakya.domain.usecases.session.GetLoginStatus;
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

    @Override public Observable<UserProfile> getProfile() {
        return dataStoreFactory.getProfile().map(userProfileRxDto -> {
            if (userProfileRxDto.getData() == null) {
                return new UserProfile("", "");
            } else {
                return userProfileRxDto.getData();
            }
        });
    }
}

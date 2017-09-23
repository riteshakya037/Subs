package io.subs.data.repository.datasource.sessions;

import io.reactivex.Observable;
import io.subs.domain.usecases.session.GetLoginStatus;

/**
 * @author Ritesh Shakya
 */

public interface ISessionDataStore {

    Observable<GetLoginStatus.LoginStatusType> getLoginStatus();

    Observable<Void> signOut();

    String getUserID();

    String getRegistrationToken();
}

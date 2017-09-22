package io.subs.data.repository.datasource;

import io.reactivex.Observable;
import io.subs.domain.usecases.session.GetLoginStatus;

/**
 * @author Ritesh Shakya
 */

public interface SessionDataStore {

    Observable<GetLoginStatus.LoginStatusType> getLoginStatus();

    Observable<Void> signOut();

    String getUserID();

    String getRegistrationToken();
}

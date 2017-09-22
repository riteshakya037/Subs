package io.subs.domain.repository;

import io.reactivex.Observable;
import io.subs.domain.usecases.session.GetLoginStatus;

/**
 * @author Ritesh Shakya
 */

public interface ISessionRepository {
    Observable<GetLoginStatus.LoginStatusType> getLoginStatus();

    Observable<Void> signOut();
}

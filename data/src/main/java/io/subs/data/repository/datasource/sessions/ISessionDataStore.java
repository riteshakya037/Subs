package io.subs.data.repository.datasource.sessions;

import io.reactivex.Observable;
import io.subs.data.helper.RxDto;
import io.subs.domain.models.UserProfile;
import io.subs.domain.usecases.session.GetLoginStatus;

/**
 * @author Ritesh Shakya
 */

public interface ISessionDataStore {

    Observable<GetLoginStatus.LoginStatusType> getLoginStatus();

    Observable<Void> signOut();

    String getUserID();

    Observable<RxDto<UserProfile>> getProfile();

}

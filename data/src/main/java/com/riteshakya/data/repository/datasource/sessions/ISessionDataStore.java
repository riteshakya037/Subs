package com.riteshakya.data.repository.datasource.sessions;

import com.riteshakya.data.helper.RxDto;
import com.riteshakya.domain.usecases.session.GetLoginStatus;

import io.reactivex.Observable;
import com.riteshakya.data.helper.RxDto;
import com.riteshakya.domain.models.UserProfile;
import com.riteshakya.domain.usecases.session.GetLoginStatus;

/**
 * @author Ritesh Shakya
 */

public interface ISessionDataStore {

    Observable<GetLoginStatus.LoginStatusType> getLoginStatus();

    Observable<Void> signOut();

    String getUserID();

    Observable<RxDto<UserProfile>> getProfile();

}

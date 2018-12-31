package com.riteshakya.domain.repository;

import com.riteshakya.domain.models.UserProfile;
import com.riteshakya.domain.usecases.session.GetLoginStatus;

import io.reactivex.Observable;

/**
 * @author Ritesh Shakya
 */

public interface ISessionRepository {
    Observable<GetLoginStatus.LoginStatusType> getLoginStatus();

    Observable<Void> signOut();

    Observable<UserProfile> getProfile();
}

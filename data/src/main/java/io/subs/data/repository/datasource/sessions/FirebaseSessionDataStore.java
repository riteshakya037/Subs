package io.subs.data.repository.datasource.sessions;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.subs.domain.usecases.session.GetLoginStatus;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class FirebaseSessionDataStore implements ISessionDataStore {
    private static final String TAG = FirebaseSessionDataStore.class.getSimpleName();
    private static final String KEY_ACTIVE_USER = "active_user";
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @Inject public FirebaseSessionDataStore() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override public Observable<GetLoginStatus.LoginStatusType> getLoginStatus() {
        if (getActiveUser() == null) {
            return Observable.just(GetLoginStatus.LoginStatusType.INACTIVE);
        } else {
            return Observable.just(GetLoginStatus.LoginStatusType.ACTIVE);
        }
    }

    @Override public Observable<Void> signOut() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override public void subscribe(CompletableEmitter e) throws Exception {
                FirebaseAuth.getInstance().signOut();
                e.onComplete();
            }
        }).toObservable();
    }

    @Override public String getUserID() {
        return getActiveUser().getUid();
    }

    @Override public String getRegistrationToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    private FirebaseUser getActiveUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}

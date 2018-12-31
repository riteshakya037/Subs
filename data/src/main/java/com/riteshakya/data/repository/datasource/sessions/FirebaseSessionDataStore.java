package com.riteshakya.data.repository.datasource.sessions;

import android.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.riteshakya.data.helper.RxDto;
import com.riteshakya.data.listeners.DatabaseCompletionListener;
import com.riteshakya.data.listeners.FirebaseValueListener;
import com.riteshakya.domain.usecases.session.GetLoginStatus;

import io.reactivex.Completable;
import io.reactivex.Observable;
import com.riteshakya.data.helper.RxDto;
import com.riteshakya.data.listeners.DatabaseCompletionListener;
import com.riteshakya.data.listeners.FirebaseValueListener;
import com.riteshakya.domain.DatabaseNames;
import com.riteshakya.domain.models.UserProfile;
import com.riteshakya.domain.usecases.session.GetLoginStatus.LoginStatusType;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class FirebaseSessionDataStore implements ISessionDataStore {
    private static final String TAG = FirebaseSessionDataStore.class.getSimpleName();
    private final DatabaseCompletionListener databaseCompletionListener;
    private final GoogleApiClient mGoogleApiClient;
    private DatabaseReference userRootRef;

    @SuppressWarnings("WeakerAccess") @Inject
        public FirebaseSessionDataStore(DatabaseCompletionListener databaseCompletionListener,
            GoogleApiClient googleApiClient) {
        this.databaseCompletionListener = databaseCompletionListener;
        this.mGoogleApiClient = googleApiClient;
    }

    private void initializeRef() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String tablePath = DatabaseNames.createPath(DatabaseNames.TABLE_USER_DATA, getUserID());
        Log.e(TAG, "FirebaseUserSubscriptionDataStore: " + tablePath);
        userRootRef = firebaseDatabase.getReference().child(tablePath);
    }

    @Override public Observable<GetLoginStatus.LoginStatusType> getLoginStatus() {
        if (getActiveUser() == null) {
            return Observable.just(GetLoginStatus.LoginStatusType.INACTIVE);
        } else {
            initializeRef();
            return getProfile().flatMap(userProfileRxDto -> {
                if (userProfileRxDto.getData() == null) {
                    return createProfile();
                }
                return Observable.just(userProfileRxDto.getData());
            }).map(userProfile -> GetLoginStatus.LoginStatusType.ACTIVE);
        }
    }

    @Override public Observable<Void> signOut() {
        return Completable.create(e -> {
            FirebaseAuth.getInstance().signOut();
            mGoogleApiClient.connect();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
                System.out.println("status = " + status.getStatusMessage());
                e.onComplete();
            });
        }).toObservable();
    }

    @Override public String getUserID() {
        return getActiveUser().getUid();
    }

    private String getUserFullName() {
        return getActiveUser().getDisplayName();
    }

    private String getUserEmail() {
        return getActiveUser().getEmail();
    }

    private Observable<UserProfile> createProfile() {
        return Observable.create(emitter -> {
            String key = DatabaseNames.USER_PROFILE;
            UserProfile userProfile = new UserProfile(getUserFullName(), getUserEmail());
            Map<String, Object> postValues = userProfile.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(key, postValues);
            databaseCompletionListener.updateChildren(userRootRef, emitter, childUpdates);
        });
    }

    @Override public Observable<RxDto<UserProfile>> getProfile() {
        final DatabaseReference userProfileRef = userRootRef.child(DatabaseNames.USER_PROFILE);
        return Observable.create(emitter -> {
            final ValueEventListener listener =
                    userProfileRef.addValueEventListener(new FirebaseValueListener<UserProfile>() {
                        @Override public void onCancelled(DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }

                        @Override public void onChildChanged(UserProfile userProfile) {
                            emitter.onNext(new RxDto<>(userProfile));
                        }
                    });

            emitter.setCancellable(() -> userProfileRef.removeEventListener(listener));
        });
    }

    private FirebaseUser getActiveUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}

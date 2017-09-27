package io.subs.data.repository.datasource.sessions;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.subs.data.helper.RxDto;
import io.subs.data.listeners.DatabaseCompletionListener;
import io.subs.data.listeners.FirebaseValueListener;
import io.subs.domain.DatabaseNames;
import io.subs.domain.models.UserProfile;
import io.subs.domain.usecases.session.GetLoginStatus.LoginStatusType;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class FirebaseSessionDataStore implements ISessionDataStore {
    private static final String TAG = FirebaseSessionDataStore.class.getSimpleName();
    private static final String KEY_ACTIVE_USER = "active_user";
    private final DatabaseReference userRootRef;

    @Inject public FirebaseSessionDataStore() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String tablePath = DatabaseNames.createPath(DatabaseNames.TALBE_USER_DATA, getUserID());
        Log.e(TAG, "FirebaseUserSubscriptionDataStore: " + tablePath);
        userRootRef = firebaseDatabase.getReference().child(tablePath);
    }

    @Override public Observable<LoginStatusType> getLoginStatus() {
        if (getActiveUser() == null) {
            return Observable.just(LoginStatusType.INACTIVE);
        } else {
            return Observable.create(new ObservableOnSubscribe<LoginStatusType>() {
                @Override
                public void subscribe(@NonNull final ObservableEmitter<LoginStatusType> emitter)
                        throws Exception {
                    getProfile().doOnNext(new Consumer<RxDto<UserProfile>>() {
                        @Override public void accept(RxDto<UserProfile> userProfile)
                                throws Exception {
                            if (userProfile.getData() == null) {
                                createProfile().subscribe();
                            }
                        }
                    }).doOnError(new Consumer<Throwable>() {
                        @Override public void accept(Throwable throwable) throws Exception {
                            emitter.onError(throwable);
                        }
                    }).subscribe();
                    emitter.onNext(LoginStatusType.ACTIVE);
                }
            });
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

    private String getUserFullName() {
        return getActiveUser().getDisplayName();
    }

    private String getUserEmail() {
        return getActiveUser().getEmail();
    }

    @Override public Observable<UserProfile> createProfile() {
        return Observable.create(new ObservableOnSubscribe<UserProfile>() {
            @Override public void subscribe(@NonNull final ObservableEmitter<UserProfile> emitter)
                    throws Exception {
                String key = DatabaseNames.USER_PROFILE;
                UserProfile userProfile = new UserProfile(getUserFullName(), getUserEmail());
                Map<String, Object> postValues = userProfile.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(key, postValues);
                userRootRef.updateChildren(childUpdates, new DatabaseCompletionListener<>(emitter));
            }
        });
    }

    @Override public Observable<RxDto<UserProfile>> getProfile() {
        final DatabaseReference userProfileRef = userRootRef.child(DatabaseNames.USER_PROFILE);
        return Observable.create(new ObservableOnSubscribe<RxDto<UserProfile>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<RxDto<UserProfile>> emitter)
                    throws Exception {
                final ValueEventListener listener = userProfileRef.addValueEventListener(
                        new FirebaseValueListener<UserProfile>() {
                            @Override public void onCancelled(DatabaseError databaseError) {
                                emitter.onError(databaseError.toException());
                            }

                            @Override public void onChildChanged(UserProfile userProfile) {
                                emitter.onNext(new RxDto<>(userProfile));
                            }
                        });

                emitter.setCancellable(new Cancellable() {
                    @Override public void cancel() throws Exception {
                        userProfileRef.removeEventListener(listener);
                    }
                });
            }
        });
    }

    @Override public String getRegistrationToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    private FirebaseUser getActiveUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}

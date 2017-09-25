package io.subs.data.repository.datasource.user_subscriptions;

import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;
import io.reactivex.subjects.PublishSubject;
import io.subs.data.DatabaseNames;
import io.subs.data.listeners.FirebaseChildListener;
import io.subs.data.repository.datasource.sessions.ISessionDataStore;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.usecases.user_subscriptions.GetUserSubscriptionList;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Action;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ritesh Shakya
 */
@Singleton public class FirebaseUserSubscriptionDataStore implements UserSubscriptionDataStore {
    private static final String TAG = "UserSubscription";
    private final PublishSubject<UserSubscriptionDto> mUpdatePublisher = PublishSubject.create();
    private DatabaseReference userSubscriptionRef;

    @Inject public FirebaseUserSubscriptionDataStore(ISessionDataStore sessionDataStore) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String tablePath = DatabaseNames.createPath(DatabaseNames.TALBE_USER_DATA,
                sessionDataStore.getUserID(), DatabaseNames.TABLE_SUBSCRIPTIONS);
        Log.e(TAG, "FirebaseUserSubscriptionDataStore: " + tablePath);
        userSubscriptionRef = firebaseDatabase.getReference().child(tablePath);
    }

    @Override
    public Observable<Void> subscriptionEntityList(GetUserSubscriptionList.Params params) {
        return observe(userSubscriptionRef);
    }

    @Override public Observable<UserSubscriptionDto> subscribe() {
        return mUpdatePublisher;
    }

    @Override
    public Observable<Void> createOrUpdateSubscription(final UserSubscription userSubscription) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override public void subscribe(@NonNull final ObservableEmitter<Void> emitter)
                    throws Exception {
                String key;
                if (TextUtils.isEmpty(userSubscription.getId())) {
                    key = userSubscriptionRef.push().getKey();
                } else {
                    key = userSubscription.getId();
                }
                Map<String, Object> postValues = userSubscription.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(key, postValues);
                userSubscriptionRef.updateChildren(childUpdates,
                        new DatabaseReference.CompletionListener() {
                            @Override public void onComplete(DatabaseError databaseError,
                                    DatabaseReference databaseReference) {
                                if (emitter.isDisposed()) {
                                    return;
                                }
                                if (databaseError == null) {
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new Throwable(databaseError.getMessage()));
                                }
                            }
                        });
            }
        });
    }

    private Observable<Void> observe(final DatabaseReference ref) {
        return Observable.create(new ObservableOnSubscribe<DataSnapshot>() {
            @Override public void subscribe(@NonNull ObservableEmitter<DataSnapshot> emitter)
                    throws Exception {
                final ChildEventListener listener =
                        ref.addChildEventListener(new FirebaseChildListener<UserSubscription>() {
                            @Override
                            public void onChildAdded(UserSubscription dataSnapshot, String s) {
                                mUpdatePublisher.onNext(
                                        new UserSubscriptionDto(dataSnapshot, Action.ADDED));
                            }

                            @Override
                            public void onChildChanged(UserSubscription dataSnapshot, String s) {
                                mUpdatePublisher.onNext(
                                        new UserSubscriptionDto(dataSnapshot, Action.UPDATED));
                            }

                            @Override public void onChildRemoved(UserSubscription dataSnapshot) {
                                mUpdatePublisher.onNext(
                                        new UserSubscriptionDto(dataSnapshot, Action.REMOVED));
                            }

                            @Override public void onCancelled(DatabaseError databaseError) {
                                mUpdatePublisher.onError(databaseError.toException());
                            }
                        });

                emitter.setCancellable(new Cancellable() {
                    @Override public void cancel() throws Exception {
                        ref.removeEventListener(listener);
                    }
                });
            }
        }).cast(Void.TYPE);
    }
}

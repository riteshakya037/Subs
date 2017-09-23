package io.subs.data.repository.datasource.user_subscriptions;

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
import io.subs.data.repository.datasource.sessions.ISessionDataStore;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.usecases.user_subscriptions.GetUserSubscriptionList;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Action;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ritesh Shakya
 */
@Singleton public class FirebaseUserSubscriptionDataStore implements UserSubscriptionDataStore {
    private static final String TAG = "UserSubscription";
    private final PublishSubject<UserSubscriptionDto> mUpdatePublisher = PublishSubject.create();
    private DatabaseReference databaseReference;

    @Inject public FirebaseUserSubscriptionDataStore(ISessionDataStore sessionDataStore) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String tablePath = DatabaseNames.createPath(DatabaseNames.TALBE_USER_DATA,
                sessionDataStore.getUserID(), DatabaseNames.TABLE_SUBSCRIPTIONS);
        Log.e(TAG, "FirebaseUserSubscriptionDataStore: " + tablePath);
        databaseReference = firebaseDatabase.getReference().child(tablePath);
    }

    @Override
    public Observable<Void> subscriptionEntityList(GetUserSubscriptionList.Params params) {
        return observe(databaseReference.child(DatabaseNames.TABLE_SUBSCRIPTIONS));
    }

    @Override public Observable<UserSubscriptionDto> subscribe() {
        return mUpdatePublisher;
    }

    private Observable<Void> observe(final DatabaseReference ref) {
        return Observable.create(new ObservableOnSubscribe<DataSnapshot>() {
            @Override public void subscribe(@NonNull ObservableEmitter<DataSnapshot> emitter)
                    throws Exception {
                final ChildEventListener listener =
                        ref.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                publishEvent("onChildAdded: ", dataSnapshot, Action.ADDED);
                            }

                            private void publishEvent(String msg, DataSnapshot dataSnapshot,
                                    Action action) {
                                UserSubscription value =
                                        dataSnapshot.getValue(UserSubscription.class);
                                value.setId(dataSnapshot.getKey());
                                Log.e(TAG, msg + value);
                                mUpdatePublisher.onNext(new UserSubscriptionDto(value, action));
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                publishEvent("onChildChanged: ", dataSnapshot, Action.UPDATED);
                            }

                            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
                                publishEvent("onChildRemoved: ", dataSnapshot, Action.REMOVED);
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override public void onCancelled(DatabaseError databaseError) {

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

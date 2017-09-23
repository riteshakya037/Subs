package io.subs.data.repository.datasource.subscriptions;

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
import io.subs.domain.models.Subscription;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.Action;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ritesh Shakya
 */
@Singleton public class FirebaseSubscriptionDataStore implements ISubscriptionDataStore {
    private static final String TAG = "FirebaseSubscriptionDat";
    private final PublishSubject<SubscriptionDto> mUpdatePublisher = PublishSubject.create();
    private DatabaseReference databaseReference;

    @Inject public FirebaseSubscriptionDataStore() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override public Observable<Void> subscriptionEntityList() {
        return observe(databaseReference.child(DatabaseNames.TABLE_SUBSCRIPTIONS));
    }

    @Override public Observable<SubscriptionDto> subscribe() {
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
                                Subscription value = dataSnapshot.getValue(Subscription.class);
                                value.setId(dataSnapshot.getKey());
                                Log.e(TAG, msg + value);
                                mUpdatePublisher.onNext(new SubscriptionDto(value, action));
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

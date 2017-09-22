package io.subs.data.repository.datasource;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.subjects.PublishSubject;
import io.subs.data.DatabaseNames;
import io.subs.data.entity.SubscriptionEntity;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link SubscriptionDataStore} implementation based on connections to Firebase.
 */
@Singleton public class FirebaseSubscriptionDataStore implements SubscriptionDataStore {
    private final PublishSubject<SubscriptionEntity> mUpdatePublisher = PublishSubject.create();
    private DatabaseReference databaseReference;

    @Inject public FirebaseSubscriptionDataStore() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override public Observable<Void> subscriptionEntityList() {
        return observe(databaseReference.child(DatabaseNames.TABLE_SUBSCRIPTIONS));
    }

    @Override public Observable<SubscriptionEntity> subscribe() {
        return mUpdatePublisher;
    }

    private Observable<Void> observe(final DatabaseReference ref) {
        return Observable.create((ObservableOnSubscribe<DataSnapshot>) emitter -> {
            final ChildEventListener listener = ref.addChildEventListener(new ChildEventListener() {
                @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mUpdatePublisher.onNext(dataSnapshot.getValue(SubscriptionEntity.class));
                }

                @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override public void onCancelled(DatabaseError databaseError) {

                }
            });

            emitter.setCancellable(() -> ref.removeEventListener(listener));
        }).cast(Void.TYPE);
    }
}

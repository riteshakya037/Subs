package com.riteshakya.data.repository.datasource.subscriptions;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.riteshakya.data.listeners.FirebaseChildListener;
import com.riteshakya.domain.usecases.subscription.SubscribeToSubscriptionUpdates;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.subjects.ReplaySubject;
import com.riteshakya.data.listeners.FirebaseChildListener;
import com.riteshakya.domain.DatabaseNames;
import com.riteshakya.domain.models.Subscription;
import com.riteshakya.domain.models.enums.SubscriptionType;
import com.riteshakya.domain.usecases.subscription.SubscribeToSubscriptionUpdates.Action;
import com.riteshakya.domain.usecases.subscription.SubscribeToSubscriptionUpdates.Params;
import com.riteshakya.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ritesh Shakya
 */
@Singleton public class FirebaseSubscriptionDataStore implements ISubscriptionDataStore {
    private final ReplaySubject<SubscribeToSubscriptionUpdates.SubscriptionDto> mUpdatePublisher = ReplaySubject.create();
    private final DatabaseReference databaseReference;

    @Inject public FirebaseSubscriptionDataStore() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override public Observable<Void> subscriptionEntityList() {
        return observe(databaseReference.child(DatabaseNames.TABLE_SUBSCRIPTIONS));
    }

    @Override public Observable<SubscribeToSubscriptionUpdates.SubscriptionDto> subscribe(SubscribeToSubscriptionUpdates.Params params) {
        if (params.getSubscriptionType() == SubscriptionType.POPULAR) {
            return mUpdatePublisher.filter(
                    subscriptionDto -> subscriptionDto.getSubscription().isPopular());
        } else {
            return mUpdatePublisher;
        }
    }

    private Observable<Void> observe(final DatabaseReference ref) {
        return Observable.create((ObservableOnSubscribe<DataSnapshot>) emitter -> {
            final ChildEventListener listener =
                    ref.addChildEventListener(new FirebaseChildListener<Subscription>() {

                        @Override public void onChildAdded(Subscription snapShot) {
                                mUpdatePublisher.onNext(new SubscribeToSubscriptionUpdates.SubscriptionDto(snapShot,
                                        SubscribeToSubscriptionUpdates.Action.ADDED));
                        }

                        @Override public void onChildChanged(Subscription snapShot) {
                            mUpdatePublisher.onNext(new SubscribeToSubscriptionUpdates.SubscriptionDto(snapShot,
                                    SubscribeToSubscriptionUpdates.Action.UPDATED));
                        }

                        @Override public void onChildRemoved(Subscription snapShot) {
                            mUpdatePublisher.onNext(new SubscribeToSubscriptionUpdates.SubscriptionDto(snapShot,
                                    SubscribeToSubscriptionUpdates.Action.REMOVED));
                        }

                        @Override public void onCancelled(DatabaseError databaseError) {
                            mUpdatePublisher.onError(databaseError.toException());
                        }
                    });

            emitter.setCancellable(() -> ref.removeEventListener(listener));
        }).cast(Void.TYPE);
    }
}

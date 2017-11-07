package io.subs.data.repository.datasource.subscriptions;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.subjects.ReplaySubject;
import io.subs.data.listeners.FirebaseChildListener;
import io.subs.domain.DatabaseNames;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.enums.SubscriptionType;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.Action;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.Params;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates.SubscriptionDto;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ritesh Shakya
 */
@Singleton public class FirebaseSubscriptionDataStore implements ISubscriptionDataStore {
    private final ReplaySubject<SubscriptionDto> mUpdatePublisher = ReplaySubject.create();
    private final DatabaseReference databaseReference;

    @Inject public FirebaseSubscriptionDataStore() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override public Observable<Void> subscriptionEntityList() {
        return observe(databaseReference.child(DatabaseNames.TABLE_SUBSCRIPTIONS));
    }

    @Override public Observable<SubscriptionDto> subscribe(Params params) {
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
                                mUpdatePublisher.onNext(new SubscriptionDto(snapShot,
                                        Action.ADDED));
                        }

                        @Override public void onChildChanged(Subscription snapShot) {
                            mUpdatePublisher.onNext(new SubscriptionDto(snapShot,
                                    Action.UPDATED));
                        }

                        @Override public void onChildRemoved(Subscription snapShot) {
                            mUpdatePublisher.onNext(new SubscriptionDto(snapShot,
                                    Action.REMOVED));
                        }

                        @Override public void onCancelled(DatabaseError databaseError) {
                            mUpdatePublisher.onError(databaseError.toException());
                        }
                    });

            emitter.setCancellable(() -> ref.removeEventListener(listener));
        }).cast(Void.TYPE);
    }
}

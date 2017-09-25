package io.subs.data.repository.datasource.subscriptions;

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
import io.subs.domain.DatabaseNames;
import io.subs.data.listeners.FirebaseChildListener;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.enums.SubscriptionType;
import io.subs.domain.usecases.subscription.GetSubscriptionList;
import io.subs.domain.usecases.subscription.SubscribeToSubscriptionUpdates;
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

    @Override public Observable<Void> subscriptionEntityList(GetSubscriptionList.Params params) {
        return observe(databaseReference.child(DatabaseNames.TABLE_SUBSCRIPTIONS), params);
    }

    @Override public Observable<SubscriptionDto> subscribe() {
        return mUpdatePublisher;
    }

    private Observable<Void> observe(final DatabaseReference ref,
            final GetSubscriptionList.Params params) {
        return Observable.create(new ObservableOnSubscribe<DataSnapshot>() {
            @Override public void subscribe(@NonNull ObservableEmitter<DataSnapshot> emitter)
                    throws Exception {
                final ChildEventListener listener =
                        ref.addChildEventListener(new FirebaseChildListener<Subscription>() {

                            @Override public void onChildAdded(Subscription snapShot, String s) {
                                if (params.getSubscriptionType() == SubscriptionType.POPULAR) {
                                    mUpdatePublisher.onNext(new SubscriptionDto(snapShot,
                                            SubscribeToSubscriptionUpdates.Action.ADDED));
                                }
                            }

                            @Override public void onChildChanged(Subscription snapShot, String s) {
                                mUpdatePublisher.onNext(new SubscriptionDto(snapShot,
                                        SubscribeToSubscriptionUpdates.Action.UPDATED));
                            }

                            @Override public void onChildRemoved(Subscription snapShot) {
                                mUpdatePublisher.onNext(new SubscriptionDto(snapShot,
                                        SubscribeToSubscriptionUpdates.Action.REMOVED));
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

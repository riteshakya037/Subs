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
import io.reactivex.functions.Function;
import io.reactivex.subjects.ReplaySubject;
import io.subs.data.listeners.DatabaseCompletionListener;
import io.subs.data.listeners.FirebaseChildListener;
import io.subs.data.repository.datasource.sessions.ISessionDataStore;
import io.subs.domain.DatabaseNames;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Action;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Params;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

import static io.subs.domain.DatabaseNames.DELETED_FLAG;

/**
 * @author Ritesh Shakya
 */
@Singleton public class FirebaseUserSubscriptionDataStore implements UserSubscriptionDataStore {
    private static final String TAG = "UserSubscription";
    private final ReplaySubject<UserSubscriptionDto> mUpdatePublisher = ReplaySubject.create();
    private final ISessionDataStore sessionDataStore;
    private final DatabaseCompletionListener databaseCompletionListener;
    private DatabaseReference userSubscriptionRef;

    @Inject public FirebaseUserSubscriptionDataStore(ISessionDataStore sessionDataStore,
            DatabaseCompletionListener databaseCompletionListener) {
        this.sessionDataStore = sessionDataStore;
        this.databaseCompletionListener = databaseCompletionListener;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String tablePath = DatabaseNames.createPath(DatabaseNames.TALBE_USER_DATA,
                sessionDataStore.getUserID(), DatabaseNames.TABLE_SUBSCRIPTIONS);
        Log.e(TAG, "FirebaseUserSubscriptionDataStore: " + tablePath);
        userSubscriptionRef = firebaseDatabase.getReference().child(tablePath);
    }

    @Override public Observable<Void> subscriptionEntityList() {
        return observe(userSubscriptionRef);
    }

    @Override public Observable<UserSubscriptionDto> subscribe(final Params params) {
        if (params.isAll()) {
            return mUpdatePublisher;
        } else {
            return filteredSubsList(params);
        }
    }

    private Observable<UserSubscriptionDto> filteredSubsList(final Params params) {
        return Observable.create(new ObservableOnSubscribe<UserSubscriptionDto>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<UserSubscriptionDto> emitter)
                    throws Exception {
                final ChildEventListener listener = userSubscriptionRef.orderByChild("cycle")
                        .equalTo(params.getSubscriptionCycle().name())
                        .addChildEventListener(new FirebaseChildListener<UserSubscription>() {
                            @Override
                            public void onChildAdded(UserSubscription dataSnapshot, String s) {
                                if (!dataSnapshot.getDeleted()) {
                                    emitter.onNext(
                                            new UserSubscriptionDto(dataSnapshot, Action.ADDED));
                                }
                            }

                            @Override
                            public void onChildChanged(UserSubscription dataSnapshot, String s) {
                                if (dataSnapshot.getDeleted()) {
                                    emitter.onNext(
                                            new UserSubscriptionDto(dataSnapshot, Action.REMOVED));
                                } else {
                                    emitter.onNext(
                                            new UserSubscriptionDto(dataSnapshot, Action.UPDATED));
                                }
                            }

                            @Override public void onChildRemoved(UserSubscription dataSnapshot) {
                                emitter.onNext(
                                        new UserSubscriptionDto(dataSnapshot, Action.REMOVED));
                            }

                            @Override public void onCancelled(DatabaseError databaseError) {
                                emitter.onError(databaseError.toException());
                            }
                        });

                emitter.setCancellable(new Cancellable() {
                    @Override public void cancel() throws Exception {
                        userSubscriptionRef.removeEventListener(listener);
                    }
                });
            }
        });
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
                databaseCompletionListener.updateChildren(userSubscriptionRef, emitter,
                        childUpdates);
            }
        });
    }

    @Override public Observable<Void> deleteSubscription(final String id) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override public void subscribe(@NonNull final ObservableEmitter<Void> emitter)
                    throws Exception {
                userSubscriptionRef.child(id).child(DELETED_FLAG).setValue(true);
                emitter.onComplete();
            }
        });
    }

    @Override public Observable<Integer> subscribeToCount() {
        return mUpdatePublisher.map(new Function<UserSubscriptionDto, Integer>() {
            Set<String> countList = new HashSet<>();

            @Override public Integer apply(@NonNull UserSubscriptionDto userSubscriptionDto)
                    throws Exception {
                if (userSubscriptionDto.getAction() == Action.ADDED) {
                    countList.add(userSubscriptionDto.getSubscription().getId());
                } else if (userSubscriptionDto.getAction() == Action.REMOVED) {
                    countList.remove(userSubscriptionDto.getSubscription().getId());
                }
                return countList.size();
            }
        });
    }

    private Observable<Void> observe(final DatabaseReference ref) {
        return Observable.create(new ObservableOnSubscribe<DataSnapshot>() {
            @Override public void subscribe(@NonNull ObservableEmitter<DataSnapshot> emitter)
                    throws Exception {
                final ChildEventListener listener = ref.orderByChild(DELETED_FLAG)
                        .equalTo(false)
                        .addChildEventListener(new FirebaseChildListener<UserSubscription>() {
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

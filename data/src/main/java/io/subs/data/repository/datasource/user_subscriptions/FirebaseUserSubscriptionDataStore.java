package io.subs.data.repository.datasource.user_subscriptions;

import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.subjects.ReplaySubject;
import io.subs.data.listeners.DatabaseCompletionListener;
import io.subs.data.listeners.FirebaseChildListener;
import io.subs.data.repository.datasource.sessions.ISessionDataStore;
import io.subs.domain.DatabaseNames;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.models.usecase_dtos.BreakdownModel;
import io.subs.domain.models.usecase_dtos.MonthlyBreakdownModel;
import io.subs.domain.models.usecase_dtos.WeeklyBreakdownModel;
import io.subs.domain.models.usecase_dtos.YearlyBreakdownModel;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Action;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.Params;
import io.subs.domain.usecases.user_subscriptions.SubscribeToUserSubscriptionUpdates.UserSubscriptionDto;
import io.subs.domain.usecases.user_subscriptions.SubscriptionBreakdownUpdates;
import io.subs.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    private final DatabaseCompletionListener databaseCompletionListener;
    private final DatabaseReference userSubscriptionRef;

    @Inject public FirebaseUserSubscriptionDataStore(ISessionDataStore sessionDataStore,
            DatabaseCompletionListener databaseCompletionListener) {
        this.databaseCompletionListener = databaseCompletionListener;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String tablePath = DatabaseNames.createPath(DatabaseNames.TABLE_USER_DATA,
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
            return filteredSubsList(params.getSubscriptionCycle());
        }
    }

    private Observable<UserSubscriptionDto> filteredSubsList(final Cycle cycle) {
        return Observable.create(emitter -> {
            final ChildEventListener listener = userSubscriptionRef.orderByChild("cycle")
                    .equalTo(cycle.name())
                    .addChildEventListener(new FirebaseChildListener<UserSubscription>() {
                        @Override
                        public void onChildAdded(UserSubscription dataSnapshot) {
                            if (!dataSnapshot.getDeleted()) {
                                emitter.onNext(new UserSubscriptionDto(dataSnapshot, Action.ADDED));
                            }
                        }

                        @Override
                        public void onChildChanged(UserSubscription dataSnapshot) {
                            if (dataSnapshot.getDeleted()) {
                                emitter.onNext(
                                        new UserSubscriptionDto(dataSnapshot, Action.REMOVED));
                            } else {
                                emitter.onNext(
                                        new UserSubscriptionDto(dataSnapshot, Action.UPDATED));
                            }
                        }

                        @Override public void onChildRemoved(UserSubscription dataSnapshot) {
                            emitter.onNext(new UserSubscriptionDto(dataSnapshot, Action.REMOVED));
                        }

                        @Override public void onCancelled(DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }
                    });

            emitter.setCancellable(() -> userSubscriptionRef.removeEventListener(listener));
        });
    }

    @Override
    public Observable<Void> createOrUpdateSubscription(final UserSubscription userSubscription) {
        return Observable.create(emitter -> {
            String key;
            // if id is null, its a freshly created subscription.
            if (TextUtils.isEmpty(userSubscription.getId())) {
                key = userSubscriptionRef.push().getKey();
            } else {
                key = userSubscription.getId();
            }
            Map<String, Object> postValues = userSubscription.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(key, postValues);
            databaseCompletionListener.updateChildren(userSubscriptionRef, emitter, childUpdates);
        });
    }

    @Override public Observable<Void> deleteSubscription(final String id) {
        return Observable.create(emitter -> {
            userSubscriptionRef.child(id).child(DELETED_FLAG).setValue(true);
            emitter.onComplete();
        });
    }

    @Override public Observable<Integer> subscribeToCount() {
        return mUpdatePublisher.map(new Function<UserSubscriptionDto, Integer>() {
            final Set<String> countList = new HashSet<>();

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

    @Override public Observable<SubscriptionBreakdownUpdates.BreakdownDto> subscribeToBreakdown(
            final SubscriptionBreakdownUpdates.Params params) {
        return mUpdatePublisher.map(
                new Function<UserSubscriptionDto, SubscriptionBreakdownUpdates.BreakdownDto>() {
                    private final List<UserSubscription> subscriptions = new ArrayList<>();

                    @Override public SubscriptionBreakdownUpdates.BreakdownDto apply(
                            @NonNull UserSubscriptionDto userSubscriptionDto) throws Exception {
                        if (userSubscriptionDto.getAction() == Action.ADDED) {
                            subscriptions.add(userSubscriptionDto.getSubscription());
                        } else if (userSubscriptionDto.getAction() == Action.UPDATED) {
                            subscriptions.set(
                                    subscriptions.indexOf(userSubscriptionDto.getSubscription()),
                                    userSubscriptionDto.getSubscription());
                        } else if (userSubscriptionDto.getAction() == Action.REMOVED) {
                            subscriptions.remove(userSubscriptionDto.getSubscription());
                        }
                        return breakDownDate(subscriptions, params);
                    }
                });
    }

    private SubscriptionBreakdownUpdates.BreakdownDto breakDownDate(
            List<UserSubscription> subscriptions, SubscriptionBreakdownUpdates.Params params) {
        if (params.getSubscriptionCycle() == Cycle.WEEKLY) {
            WeeklyBreakdownModel weeklyBreakdownModel = new WeeklyBreakdownModel();
            for (UserSubscription userSubscription : subscriptions) {
                weeklyBreakdownModel.addData(userSubscription.getJodaFirstBill().getDayOfWeek(),
                        userSubscription.getSubscriptionAmount());
            }
            return new SubscriptionBreakdownUpdates.BreakdownDto(weeklyBreakdownModel);
        } else if (params.getSubscriptionCycle() == Cycle.MONTHLY) {
            MonthlyBreakdownModel monthlyBreakdownModel = new MonthlyBreakdownModel();
            for (UserSubscription userSubscription : subscriptions) {
                Calendar ca1 = Calendar.getInstance();
                ca1.setTime(userSubscription.getFirstBill());
                ca1.setMinimalDaysInFirstWeek(1);
                int wk = ca1.get(Calendar.WEEK_OF_MONTH);
                monthlyBreakdownModel.addData(wk, userSubscription.getSubscriptionAmount());
            }
            return new SubscriptionBreakdownUpdates.BreakdownDto(monthlyBreakdownModel);
        } else if (params.getSubscriptionCycle() == Cycle.YEARLY) {
            YearlyBreakdownModel yearlyBreakdownModel = new YearlyBreakdownModel();
            for (UserSubscription userSubscription : subscriptions) {
                yearlyBreakdownModel.addData(userSubscription.getJodaFirstBill().getMonthOfYear(),
                        userSubscription.getSubscriptionAmount());
            }
            return new SubscriptionBreakdownUpdates.BreakdownDto(yearlyBreakdownModel);
        } else {
            return new SubscriptionBreakdownUpdates.BreakdownDto(new BreakdownModel() {
                @Override protected int getValueCount() {
                    return 0;
                }
            });
        }
    }

    @Override
    public Observable<Float> subscribeToExpenses(final SubscriptionExpenseUpdates.Params params) {
        return filteredSubsList(params.getSubscriptionCycle()).map(
                new Function<UserSubscriptionDto, Float>() {
                    private final List<UserSubscription> subscriptions = new ArrayList<>();

                    @Override public Float apply(@NonNull UserSubscriptionDto userSubscriptionDto)
                            throws Exception {
                        if (userSubscriptionDto.getAction() == Action.ADDED
                                || userSubscriptionDto.getAction() == Action.UPDATED) {
                            if (subscriptions.indexOf(userSubscriptionDto.getSubscription())
                                    == -1) {
                                subscriptions.add(userSubscriptionDto.getSubscription());
                            } else {
                                subscriptions.set(subscriptions.indexOf(
                                        userSubscriptionDto.getSubscription()),
                                        userSubscriptionDto.getSubscription());
                            }
                        } else if (userSubscriptionDto.getAction() == Action.REMOVED) {
                            subscriptions.remove(userSubscriptionDto.getSubscription());
                        }
                        float value = 0;
                        for (UserSubscription userSubscription : subscriptions) {
                            value += userSubscription.getSubscriptionAmount();
                        }
                        return value;
                    }
                });
    }

    private Observable<Void> observe(final DatabaseReference ref) {
        return Observable.create((ObservableOnSubscribe<DataSnapshot>) emitter -> {
            final ChildEventListener listener = ref.orderByChild(DELETED_FLAG)
                    .equalTo(false)
                    .addChildEventListener(new FirebaseChildListener<UserSubscription>() {
                        @Override
                        public void onChildAdded(UserSubscription dataSnapshot) {
                            mUpdatePublisher.onNext(
                                    new UserSubscriptionDto(dataSnapshot, Action.ADDED));
                        }

                        @Override
                        public void onChildChanged(UserSubscription dataSnapshot) {
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

            emitter.setCancellable(() -> ref.removeEventListener(listener));
        }).cast(Void.TYPE);
    }
}

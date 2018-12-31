package com.riteshakya.subs.views.screens.create_subscriptions;

import com.riteshakya.subs.views.component.BaseSpinner;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import com.riteshakya.subs.mvp.BaseRxPresenter;
import com.riteshakya.subs.repository.SpinnerDataRepository;
import com.riteshakya.subs.views.component.BaseSpinner;
import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.domain.models.enums.Currency;
import com.riteshakya.domain.usecases.user_subscriptions.CreateOrUpdateSubscription;
import com.riteshakya.domain.usecases.user_subscriptions.DeleteSubscription;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

import static com.riteshakya.domain.models.constants.Constants.DATE_FORMAT;

/**
 * @author Ritesh Shakya
 */

public class CreateSubscriptionPresenterImpl extends BaseRxPresenter
        implements CreateSubscriptionPresenter {

    private CreateSubscriptionView createSubscriptionView;
    private final CreateOrUpdateSubscription createOrUpdateSubscription;
    private final DeleteSubscription deleteSubscription;

    @Inject
    public CreateSubscriptionPresenterImpl(CreateOrUpdateSubscription createOrUpdateSubscription,
            DeleteSubscription deleteSubscription) {
        this.createOrUpdateSubscription = createOrUpdateSubscription;
        this.deleteSubscription = deleteSubscription;
    }

    @Override public void setView(CreateSubscriptionView createSubscriptionView) {
        this.createSubscriptionView = createSubscriptionView;
    }

    @Override public void initializeFields(UserSubscription userSubscription) {
        createSubscriptionView.setName(userSubscription.getSubscriptionName());
        createSubscriptionView.setAmount(userSubscription.getSubscriptionAmount());
        createSubscriptionView.setIcon(userSubscription.getSubscriptionIcon());
        createSubscriptionView.setDescription(userSubscription.getSubscriptionDescription());
        createSubscriptionView.setCycle(userSubscription.getSubscriptionCycle().name());
        createSubscriptionView.setFirstBill(
                new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(
                        userSubscription.getFirstBill()));
        createSubscriptionView.setDuration(userSubscription.getSubscriptionDuration().name());
        createSubscriptionView.setReminder(userSubscription.getSubscriptionReminder().name());
        createSubscriptionView.setSubscriptionCurrency(
                userSubscription.getSubscriptionCurrency().name());
        createSubscriptionView.setColor(userSubscription.getLayoutColor());
    }

    @Override public List<BaseSpinner> getCycleList() {
        return SpinnerDataRepository.getCycleList();
    }

    @Override public List<BaseSpinner> getDurationList() {
        return SpinnerDataRepository.getDurationList();
    }

    @Override public List<BaseSpinner> getReminderList() {
        return SpinnerDataRepository.getReminderList();
    }

    @Override public List<BaseSpinner> getCurrencyList() {
        return SpinnerDataRepository.getCurrencyList();
    }

    @Override public void addCard(UserSubscription userSubscription) {
        manage(createOrUpdateSubscription.execute(new DisposableObserver<Void>() {
            @Override public void onNext(@NonNull Void aVoid) {
            }

            @Override public void onError(@NonNull Throwable e) {
                createSubscriptionView.cardCreationError(e.getMessage());
            }

            @Override public void onComplete() {
                createSubscriptionView.cardSuccessfullyCreated();
            }
        }, userSubscription));
    }

    @Override public void deleteCard(String id) {
        deleteSubscription.execute(new DisposableObserver<Void>() {
            @Override public void onNext(@NonNull Void aVoid) {

            }

            @Override public void onError(@NonNull Throwable e) {

            }

            @Override public void onComplete() {
                createSubscriptionView.cardSuccessfullyCreated();
            }
        }, id);
    }

    @Override public void initializeValidationObservers(
            List<ObservableSource<Boolean>> textValidationObservable) {
        manage(Observable.combineLatest(textValidationObservable, objects -> {
            // // TODO: 0025, September 25, 2017 Find a better solution
            for (Object o : objects) {
                if (!(boolean) o) return false;
            }
            return true;
        }).subscribe(
                allValidation -> createSubscriptionView.setAddButtonVisibility(allValidation)));
    }

    @Override public void initializeCurrencyObserver(Observable<String> changeObservable) {
        manage(changeObservable.subscribe(
                s -> createSubscriptionView.setAmountCurrencyMask(Currency.valueOf(s).getSymbol())));
    }
}

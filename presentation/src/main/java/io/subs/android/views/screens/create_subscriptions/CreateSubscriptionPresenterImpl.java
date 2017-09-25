package io.subs.android.views.screens.create_subscriptions;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.repository.SpinnerDataRepository;
import io.subs.android.views.component.BaseSpinner;
import io.subs.domain.models.UserSubscription;
import io.subs.domain.models.enums.Currency;
import io.subs.domain.usecases.user_subscriptions.CreateOrUpdateSubscription;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

import static io.subs.domain.models.constants.Constants.DATE_FORMAT;

/**
 * @author Ritesh Shakya
 */

public class CreateSubscriptionPresenterImpl extends BaseRxPresenter
        implements CreateSubscriptionPresenter {

    private CreateSubscriptionView createSubscriptionView;
    private CreateOrUpdateSubscription createOrUpdateSubscription;

    @Inject
    public CreateSubscriptionPresenterImpl(CreateOrUpdateSubscription createOrUpdateSubscription) {
        this.createOrUpdateSubscription = createOrUpdateSubscription;
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

    @Override public void initializeValidationObservers(
            List<ObservableSource<Boolean>> textValidationObservable) {
        manage(Observable.combineLatest(textValidationObservable,
                new Function<Object[], Boolean>() {
                    @Override public Boolean apply(@NonNull Object[] objects) throws Exception {
                        // // TODO: 0025, September 25, 2017 Find a better solution
                        for (Object o : objects) {
                            if (!(boolean) o) return false;
                        }
                        return true;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override public void accept(Boolean allValidation) throws Exception {
                createSubscriptionView.setAddButtonVisibility(allValidation);
            }
        }));
    }

    @Override public void initializeCurrencyObserver(Observable<String> changeObservable) {
        manage(changeObservable.subscribe(new Consumer<String>() {
            @Override public void accept(String s) throws Exception {
                createSubscriptionView.setAmountCurrencyMask(Currency.valueOf(s).getSymbol());
            }
        }));
    }
}

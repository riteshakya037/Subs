package io.subs.android.views.screens.create_subscriptions;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.subs.android.mvp.IPresenter;
import io.subs.android.views.component.BaseSpinner;
import io.subs.domain.models.UserSubscription;
import java.util.List;

/**
 * @author Ritesh Shakya
 */

public interface CreateSubscriptionPresenter extends IPresenter {
    void setView(CreateSubscriptionView createSubscriptionView);

    void initializeFields(UserSubscription userSubscription);

    List<BaseSpinner> getCycleList();

    List<BaseSpinner> getDurationList();

    List<BaseSpinner> getReminderList();

    List<BaseSpinner> getCurrencyList();

    void addCard(UserSubscription userSubscription);

    void deleteCard(String id);

    void initializeValidationObservers(List<ObservableSource<Boolean>> textValidationObservable);

    void initializeCurrencyObserver(Observable<String> changeObservable);

    interface CreateSubscriptionView {
        void setName(String subscriptionName);

        void setIcon(String subscriptionIcon);

        void setDescription(String subscriptionDescription);

        void setCycle(String subscriptionCycle);

        void setFirstBill(String firstBill);

        void setDuration(String subscriptionDuration);

        void setReminder(String subscriptionReminder);

        void setSubscriptionCurrency(String subscriptionCurrency);

        void setColor(String layoutColor);

        void setAddButtonVisibility(Boolean allValidation);

        void setAmountCurrencyMask(String symbol);

        void setAmount(float subscriptionAmount);

        void cardCreationError(String message);

        void cardSuccessfullyCreated();

    }
}

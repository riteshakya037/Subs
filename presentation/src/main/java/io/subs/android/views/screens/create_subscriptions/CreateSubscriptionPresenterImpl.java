package io.subs.android.views.screens.create_subscriptions;

import io.subs.android.mvp.BaseRxPresenter;
import io.subs.android.repository.SpinnerDataRepository;
import io.subs.android.views.component.BaseSpinner;
import io.subs.domain.models.UserSubscription;
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

    @Inject public CreateSubscriptionPresenterImpl() {
    }

    @Override public void setView(CreateSubscriptionView createSubscriptionView) {
        this.createSubscriptionView = createSubscriptionView;
    }

    @Override public void initializeFields(UserSubscription userSubscription) {
        createSubscriptionView.setName(userSubscription.getSubscriptionName());
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
}

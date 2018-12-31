package com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail;

import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.domain.models.enums.Cycle;
import com.riteshakya.domain.usecases.user_subscriptions.GetSubscriptionsForToday;
import com.riteshakya.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;
import com.riteshakya.subs.mvp.BaseRxPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * @author Ritesh Shakya
 */

public class DetailExpensePresenterImpl extends BaseRxPresenter implements DetailExpensePresenter {
    private DetailExpenseView detailExpenseView;
    private final SubscriptionExpenseUpdates subscriptionExpenseUpdates;
    private GetSubscriptionsForToday getTotalForToday;
    private float weeklySum;
    private float monthlySum;
    private float yearlySum;

    @Inject
    public DetailExpensePresenterImpl(SubscriptionExpenseUpdates subscriptionExpenseUpdates, GetSubscriptionsForToday getTotalForToday) {
        this.subscriptionExpenseUpdates = subscriptionExpenseUpdates;
        this.getTotalForToday = getTotalForToday;
    }

    @Override public void setView(DetailExpenseView mainActivityView) {
        this.detailExpenseView = mainActivityView;
    }

    @Override public void initializeObservers() {
        initializeIndividualObservers(Cycle.WEEKLY, new ExpenseListener() {
            @Override protected void individualValue(float values) {
                weeklySum = values;
                detailExpenseView.updateWeeklySum(values);
            }
        });
        initializeIndividualObservers(Cycle.MONTHLY, new ExpenseListener() {
            @Override protected void individualValue(float values) {
                monthlySum = values;
                detailExpenseView.updateMonthlySum(values);
            }
        });
        initializeIndividualObservers(Cycle.YEARLY, new ExpenseListener() {
            @Override protected void individualValue(float values) {
                yearlySum = values;
                detailExpenseView.updateYearlySum(values);
            }
        });

        manage(getTotalForToday.execute(new DisposableObserver<List<UserSubscription>>() {
            @Override
            public void onNext(List<UserSubscription> userSubscriptions) {
                float value = 0f;
                for (UserSubscription subscription :
                        userSubscriptions) {
                    value += subscription.getSubscriptionAmount();
                }
                detailExpenseView.updateTodaySum(value);
                detailExpenseView.setSubscriptionsToday(userSubscriptions);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, null));
    }

    private void initializeIndividualObservers(Cycle cycle, final ExpenseListener expenseListener) {
        manage(subscriptionExpenseUpdates.execute(new DisposableObserver<Float>() {
            @Override public void onNext(@NonNull Float aFloat) {
                expenseListener.update(aFloat);
            }

            @Override public void onError(@NonNull Throwable e) {

            }

            @Override public void onComplete() {

            }
        }, SubscriptionExpenseUpdates.Params.forCase(cycle)));
    }

    private void calculateTotal() {
        detailExpenseView.updateTotalSum(weeklySum * 52 + monthlySum * 12 + yearlySum);
    }

    private abstract class ExpenseListener {

        void update(float values) {
            individualValue(values);
            DetailExpensePresenterImpl.this.calculateTotal();
        }

        protected abstract void individualValue(float values);
    }
}

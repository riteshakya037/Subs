package io.subs.android.views.screens.main_screen.user_details.expense_detail;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.usecases.user_subscriptions.SubscriptionExpenseUpdates;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class DetailExpensePresenterImpl extends BaseRxPresenter implements DetailExpensePresenter {
    private DetailExpenseView detailExpenseView;
    private SubscriptionExpenseUpdates subscriptionExpenseUpdates;
    private float weeklySum;
    private float monthlySum;
    private float yearlySum;

    @Inject
    public DetailExpensePresenterImpl(SubscriptionExpenseUpdates subscriptionExpenseUpdates) {
        this.subscriptionExpenseUpdates = subscriptionExpenseUpdates;
    }

    @Override public void setView(DetailExpenseView mainActivityView) {
        this.detailExpenseView = mainActivityView;
    }

    @Override public void initialize() {
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

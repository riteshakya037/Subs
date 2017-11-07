package io.subs.android.views.screens.main_screen.user_details.expense_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.BindView;
import io.subs.android.R;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import io.subs.android.views.component.ExpenseView;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") public class DetailExpenseFragment extends BaseFragment
        implements DetailExpensePresenter.DetailExpenseView {
    @Inject DetailExpensePresenter detailExpensePresenter;

    @BindView(R.id.fragment_detail_expense_weekly) ExpenseView tvWeeklyExpense;
    @BindView(R.id.fragment_detail_expense_monthly) ExpenseView tvMonthlyExpense;
    @BindView(R.id.fragment_detail_expense_yearly) ExpenseView tvYearlyExpense;
    @BindView(R.id.fragment_detail_expense_total) ExpenseView tvTotalExpense;

    public static Fragment createInstance() {
        return new DetailExpenseFragment();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_detal_expense;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        this.detailExpensePresenter.setView(this);
        if (savedInstanceState == null) {
            detailExpensePresenter.initializeObservers();
        }
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(detailExpensePresenter);
    }

    @Override public void updateWeeklySum(float value) {
        tvWeeklyExpense.setExpenseValue(value);
    }

    @Override public void updateMonthlySum(float value) {
        tvMonthlyExpense.setExpenseValue(value);
    }

    @Override public void updateYearlySum(float value) {
        tvYearlyExpense.setExpenseValue(value);
    }

    @Override public void updateTotalSum(float value) {
        tvTotalExpense.setExpenseValue(value);
    }
}

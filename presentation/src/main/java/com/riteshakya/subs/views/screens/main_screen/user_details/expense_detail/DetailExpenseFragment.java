package com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.UserSubscriptionComponent;
import com.riteshakya.subs.views.adapters.TodaySubscriptionAdaptor;
import com.riteshakya.subs.views.base.BaseFragment;
import com.riteshakya.subs.views.component.ExpenseView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") public class DetailExpenseFragment extends BaseFragment
        implements DetailExpensePresenter.DetailExpenseView {
    @Inject DetailExpensePresenter detailExpensePresenter;

    @BindView(R.id.fragment_detail_expense_today)
    ExpenseView tvTodayExpense;
    @BindView(R.id.fragment_detail_expense_weekly) ExpenseView tvWeeklyExpense;
    @BindView(R.id.fragment_detail_expense_monthly) ExpenseView tvMonthlyExpense;
    @BindView(R.id.fragment_detail_expense_yearly) ExpenseView tvYearlyExpense;
    @BindView(R.id.fragment_detail_expense_total) ExpenseView tvTotalExpense;
    @BindView(R.id.fragment_subscription_list)
    RecyclerView recyclerView;
    @BindView(R.id.item_subscription_count)
    TextView subscriptionCount;
    @Inject
    TodaySubscriptionAdaptor adaptor;
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
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

    @Override
    public void updateTodaySum(Float value) {
        tvTodayExpense.setExpenseValue(value);
    }

    @Override
    public void setSubscriptionsToday(List<UserSubscription> value) {
        adaptor.setSubscription(value);
        subscriptionCount.setText("Subs ( " + value.size() + " )");
    }
}

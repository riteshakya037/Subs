package com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail;

import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

import java.util.List;

/**
 * @author Ritesh Shakya
 */

public interface DetailExpensePresenter extends IPresenter {

    void setView(DetailExpenseView detailExpenseView);

    void initializeObservers();

    interface DetailExpenseView extends IView {

        void updateWeeklySum(float value);

        void updateMonthlySum(float value);

        void updateYearlySum(float value);

        void updateTotalSum(float value);

        void updateTodaySum(Float value);

        void setSubscriptionsToday(List<UserSubscription> value);
    }
}

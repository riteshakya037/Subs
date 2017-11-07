package io.subs.android.views.screens.main_screen.user_details.expense_detail;

import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

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
    }
}

package com.riteshakya.subs.views.screens.main_screen.user_details.additional_details;

import com.riteshakya.subs.mvp.IPresenter;
import com.riteshakya.subs.mvp.IView;

/**
 * @author Ritesh Shakya
 */

public interface DetailBreakDownPresenter extends IPresenter {

    void setView(DetailBreakDownView detailBreakDownView);

    void initializeObservers();

    int getMaxHeight(float[] values);

    interface DetailBreakDownView extends IView {

        void updateWeeklyChart(float[] values);

        void updateMonthlyChart(float[] values);

        void updateYearlyChart(float[] values);
    }
}

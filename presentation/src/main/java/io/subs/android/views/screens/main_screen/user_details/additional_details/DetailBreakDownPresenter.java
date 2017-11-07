package io.subs.android.views.screens.main_screen.user_details.additional_details;

import io.subs.android.mvp.IPresenter;
import io.subs.android.mvp.IView;

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

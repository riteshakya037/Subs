package io.subs.android.views.screens.main_screen.user_details.additional_details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import butterknife.BindView;
import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;
import io.subs.android.R;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.views.base.BaseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class DetailBreakDownFragment extends BaseFragment
        implements DetailBreakDownPresenter.DetailBreakDownView {
    @Inject DetailBreakDownPresenter detailBreakDownPresenter;

    @BindView(R.id.fragment_subs_detail_weekly) LineChartView cvWeekly;
    @BindView(R.id.fragment_subs_detail_monthly) LineChartView cvMonthly;
    @BindView(R.id.fragment_subs_detail_yearly) LineChartView cvYearly;

    public static Fragment createInstance() {
        return new DetailBreakDownFragment();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_detail_breakdown;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        this.detailBreakDownPresenter.setView(this);
        if (savedInstanceState == null) {
            detailBreakDownPresenter.initializeObservers();
        }
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(detailBreakDownPresenter);
    }

    @Override public void updateWeeklyChart(float[] values) {
        populateChart(getContext().getResources().getStringArray(R.array.days_of_the_week), values,
                cvWeekly);
    }

    @Override public void updateMonthlyChart(float[] values) {
        populateChart(getContext().getResources().getStringArray(R.array.week_of_the_month), values,
                cvMonthly);
    }

    @Override public void updateYearlyChart(float[] values) {
        populateChart(getContext().getResources().getStringArray(R.array.month_of_the_year), values,
                cvYearly);
    }

    private void populateChart(String[] labels, float[] values, LineChartView graphView) {
        LineSet dataSet = new LineSet(labels, values);
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorGrey))
                .setFill(ContextCompat.getColor(getContext(), android.R.color.transparent))
                .setDotsColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setThickness(4)
                .setDashed(new float[] { 10f, 10f })
                .beginAt(0);
        graphView.addData(dataSet);
        // Chart
        graphView.setBorderSpacing(Tools.fromDpToPx(15))
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setLabelsColor(ContextCompat.getColor(getContext(), R.color.colorGrey))
                .setXAxis(false)
                .setYAxis(false);
        graphView.animate();
        graphView.show();
    }
}
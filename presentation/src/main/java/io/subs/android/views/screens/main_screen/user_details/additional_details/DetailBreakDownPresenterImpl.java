package io.subs.android.views.screens.main_screen.user_details.additional_details;

import android.content.Context;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.usecases.user_subscriptions.SubscriptionBreakdownUpdates;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class DetailBreakDownPresenterImpl extends BaseRxPresenter
        implements DetailBreakDownPresenter {
    private DetailBreakDownView detailBreakDownView;
    private Context context;
    private SubscriptionBreakdownUpdates subscriptionBreakdownUpdates;

    @Inject public DetailBreakDownPresenterImpl(Context context,
            SubscriptionBreakdownUpdates subscriptionBreakdownUpdates) {
        this.context = context;
        this.subscriptionBreakdownUpdates = subscriptionBreakdownUpdates;
    }

    @Override public void setView(DetailBreakDownView mainActivityView) {
        this.detailBreakDownView = mainActivityView;
    }

    @Override public void initialize() {
        detailBreakDownView.updateWeeklyChart(new float[7]);
        detailBreakDownView.updateMonthlyChart(new float[5]);
        detailBreakDownView.updateYearlyChart(new float[12]);
    }

    @Override public void initializeObservers() {
        initializeIndividualObservers(Cycle.WEEKLY, new BreakDownListener() {
            @Override public void update(float[] values) {
                detailBreakDownView.updateWeeklyChart(values);
            }
        });
        initializeIndividualObservers(Cycle.MONTHLY, new BreakDownListener() {
            @Override public void update(float[] values) {
                detailBreakDownView.updateMonthlyChart(values);
            }
        });
        initializeIndividualObservers(Cycle.YEARLY, new BreakDownListener() {
            @Override public void update(float[] values) {
                detailBreakDownView.updateYearlyChart(values);
            }
        });
    }

    @Override public int getMaxHeight(float[] values) {
        int max = (int) values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = (int) values[i];
            }
        }
        return (max + (max % 2 == 0 ? 2 : 1));
    }

    private void initializeIndividualObservers(Cycle cycle,
            final BreakDownListener breakDownListener) {
        manage(subscriptionBreakdownUpdates.execute(
                new DisposableObserver<SubscriptionBreakdownUpdates.BreakdownDto>() {
                    @Override public void onNext(
                            @NonNull SubscriptionBreakdownUpdates.BreakdownDto breakdownDto) {
                        breakDownListener.update(breakdownDto.getmData());
                    }

                    @Override public void onError(@NonNull Throwable e) {

                    }

                    @Override public void onComplete() {

                    }
                }, SubscriptionBreakdownUpdates.Params.forCase(cycle)));
    }

    private interface BreakDownListener {
        void update(float[] values);
    }
}

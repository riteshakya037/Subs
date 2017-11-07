package io.subs.android.views.screens.main_screen.user_details.additional_details;

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
    private final SubscriptionBreakdownUpdates subscriptionBreakdownUpdates;

    @Inject
    public DetailBreakDownPresenterImpl(SubscriptionBreakdownUpdates subscriptionBreakdownUpdates) {
        this.subscriptionBreakdownUpdates = subscriptionBreakdownUpdates;
    }

    @Override public void setView(DetailBreakDownView mainActivityView) {
        this.detailBreakDownView = mainActivityView;
    }

    @Override public void initializeObservers() {
        initializeIndividualObservers(Cycle.WEEKLY,
                values -> detailBreakDownView.updateWeeklyChart(values));
        initializeIndividualObservers(Cycle.MONTHLY,
                values -> detailBreakDownView.updateMonthlyChart(values));
        initializeIndividualObservers(Cycle.YEARLY,
                values -> detailBreakDownView.updateYearlyChart(values));
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
                        breakDownListener.update(breakdownDto.getData());
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

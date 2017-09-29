package io.subs.android.views.screens.main_screen.user_subscriptions;

import android.support.v7.widget.RecyclerView;
import io.reactivex.Observable;
import io.subs.android.mvp.IPresenter;
import io.subs.android.views.base.LoadDataView;
import io.subs.android.views.component.BaseSpinner;
import java.util.List;

/**
 * @author Ritesh Shakya
 */

public interface UserSubscriptionListPresenter extends IPresenter {

    void setView(UserSubscriptionListView userSubscriptionListView);

    void initialize();

    List<BaseSpinner> getCycleList();

    void initializeAdaptor();

    void openAddSubscription();

    void initializeCycleObserver(Observable<String> changeObservable);

    interface UserSubscriptionListView extends LoadDataView {

        void setAdapter(RecyclerView.Adapter addSubscriptionAdaptor);

        void isAddEnabled(boolean isValid);
    }
}

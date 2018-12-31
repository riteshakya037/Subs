package com.riteshakya.subs.views.screens.add_subscription;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import com.riteshakya.subs.mvp.BaseRxPresenter;
import com.riteshakya.subs.views.adapters.AddSubscriptionPagerAdapter;
import com.riteshakya.domain.usecases.subscription.GetSubscriptionList;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class AddSubscriptionPresenterImpl extends BaseRxPresenter
        implements AddSubscriptionPresenter {
    private final AddSubscriptionPagerAdapter mPagerAdapter;
    private final GetSubscriptionList getSubscriptionList;
    private AddSubscriptionView addSubscriptionView;

    @Inject public AddSubscriptionPresenterImpl(AddSubscriptionPagerAdapter mPagerAdapter,
            GetSubscriptionList getSubscriptionList) {
        this.mPagerAdapter = mPagerAdapter;
        this.getSubscriptionList = getSubscriptionList;
    }

    @Override public void setView(AddSubscriptionView addSubscriptionView) {
        this.addSubscriptionView = addSubscriptionView;
    }

    @Override public void initialize() {
        getSubscriptionList();
    }

    @Override public void initializeAdaptor() {
        this.addSubscriptionView.setAdapter(mPagerAdapter);
    }

    private void getSubscriptionList() {
        this.getSubscriptionList.execute(new DisposableObserver<Void>() {
            @Override public void onNext(@NonNull Void aVoid) {

            }

            @Override public void onError(@NonNull Throwable e) {

            }

            @Override public void onComplete() {

            }
        }, null);
    }
}

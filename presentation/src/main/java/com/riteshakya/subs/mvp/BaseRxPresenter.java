package com.riteshakya.subs.mvp;

import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 * A base class for presenters to provider some utility functionality to manage rx Disposables.
 * When using this presenter it's important
 * that the lifecycle methods are called at the relevant times.
 */
public abstract class BaseRxPresenter implements IPresenter {

    private final List<Disposable> mDisposables = new ArrayList<>();

    /**
     * Add a Disposable to be tracked such that when this presenter is destroyed the Disposable
     * will be disposed with it.
     */
    protected void manage(Disposable Disposable) {
        mDisposables.add(Disposable);
    }

    @Override public void onStart() {
    }

    @Override public void onDestroy() {
        for (Disposable disposable : mDisposables) {
            disposable.dispose();
        }
        mDisposables.clear();
    }
}

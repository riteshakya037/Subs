package io.subs.android.views.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import io.subs.android.di.HasComponent;
import io.subs.android.mvp.IPresenter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class DaggerBaseActivity<C> extends BaseActivity implements HasComponent<C> {

    private final Collection<IPresenter> mPresenters = new ArrayList<>();
    private C baseComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        this.getApplicationComponent().inject(this);
        this.initializeActivity(savedInstanceState);
    }

    @Override protected void onResume() {
        super.onResume();
        for (IPresenter presenter : mPresenters) {
            presenter.onCreate();
        }
    }

    public void registerPresenter(@NonNull IPresenter... presenters) {
        Collections.addAll(mPresenters, presenters);
    }

    @Override protected void onStart() {
        super.onStart();
        for (IPresenter presenter : mPresenters) {
            presenter.onStart();
        }
    }

    @Override protected void onStop() {
        super.onStop();
        for (IPresenter presenter : mPresenters) {
            presenter.onStop();
        }
    }

    private void initializeInjector() {
        this.baseComponent = getInjector();
    }

    protected abstract C getInjector();

    @Override public C getComponent() {
        return baseComponent;
    }
}

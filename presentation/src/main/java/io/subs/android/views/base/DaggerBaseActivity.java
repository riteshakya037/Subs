package io.subs.android.views.base;

import android.os.Bundle;
import io.subs.android.di.HasComponent;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class DaggerBaseActivity<C> extends BaseActivity implements HasComponent<C> {

    private C baseComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        this.getApplicationComponent().inject(this);
        this.initializeActivity(savedInstanceState);
    }

    private void initializeInjector() {
        this.baseComponent = getInjector();
    }

    protected abstract C getInjector();

    @Override public C getComponent() {
        return baseComponent;
    }
}

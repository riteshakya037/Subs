package io.subs.android.di.modules.base;

/**
 * @author Ritesh Shakya
 */

public class InjectableModule<T> {
    private T mActivity;

    protected InjectableModule() {
    }

    protected InjectableModule(T mActivity) {
        this.mActivity = mActivity;
    }

    protected T getBoundClass() {
        return mActivity;
    }
}


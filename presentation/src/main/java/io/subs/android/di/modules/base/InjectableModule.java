package io.subs.android.di.modules.base;

/**
 * @author Ritesh Shakya
 */

public class InjectableModule<T> {
    private T mActivity;

    public InjectableModule() {
    }

    public InjectableModule(T mActivity) {
        this.mActivity = mActivity;
    }

    public T getBoundClass() {
        return mActivity;
    }
}


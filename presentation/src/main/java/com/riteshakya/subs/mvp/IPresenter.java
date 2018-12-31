package com.riteshakya.subs.mvp;

/**
 * Interface definition of a MVP Presenter.
 */
public interface IPresenter {

    /**
     * Lifecycle callback that is invoked from onStart of the Fragment/Activity managing the
     * presenter
     */
    void onStart();

    /**
     * Lifecycle callback that is invoked from onDestroy of the Fragment/Activity managing the
     * presenter
     */
    void onDestroy();
}

package io.subs.android;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.subs.domain.executor.IPostExecutionThread;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * MainThread (UI Thread) implementation based on a {@link Scheduler}
 * which will execute actions on the Android UI thread
 */
@Singleton public class UIThread implements IPostExecutionThread {

    @Inject UIThread() {
    }

    @Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
